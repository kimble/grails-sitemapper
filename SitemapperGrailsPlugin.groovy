import grails.plugins.sitemapper.*
import grails.plugins.sitemapper.impl.*
import grails.plugins.sitemapper.artefact.SitemapperArtefactHandler

import org.apache.http.impl.client.DefaultHttpClient
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class SitemapperGrailsPlugin {

  def version = "0.7.1"
  def grailsVersion = "1.3.0 > *"
  def dependsOn = [:]
  def pluginExcludes = [
      "grails-app/views/error.gsp",
      "**/demo/**"
  ]

  def author = "Kim A. Betti"
  def authorEmail = "kim@developer-b.com"
  def title = "Sitemapper"
  def description = 'Autogeneration of sitemaps, see sitemaps.org for more information about sitemaps.'
  def documentation = "https://www.github.com/kimble/grails-sitemapper"

  def artefacts = [SitemapperArtefactHandler]

  def watchedResources = [
      "file:./grails-app/sitemapper/**/*Sitemapper.groovy",
      "file:./plugins/*/grails-app/sitemapper/**/*Sitemapper.groovy"
  ]

  def doWithSpring = {

    application.sitemapperClasses.each { mapperClass ->
      log.debug "Registering sitemapper class ${mapperClass.name} as sitemapper / bean"
      "${mapperClass.name}Sitemapper"(mapperClass.clazz) { bean ->
        bean.autowire = true
      }
    }

    sitemapServerUrlResolver(ConfigSitemapServerUrlResolver)
    sitemapWriter(XmlSitemapWriter) { bean ->
      bean.autowire = true
    }

    searchEnginePinger(SearchEnginePinger) {
      searchEnginePingUrls = ConfigurationHolder.config?.searchEnginePingUrls ?: [:]
      sitemapServerUrlResolver = ref("sitemapServerUrlResolver")
      httpClient = new DefaultHttpClient()
    }
  }

  def onChange = { event ->
    if (application.isArtefactOfType(SitemapperArtefactHandler.TYPE, event.source)) {
      def mapperClass = application.addArtefact(SitemapperArtefactHandler.TYPE, event.source)
      def beanDefinitions = beans {

        // Redefine the sitemapper bean
        "${mapperClass.name}Sitemapper"(mapperClass.clazz) { bean ->
          bean.autowire = true
        }

        // Contains references to the sitemappers so
        // it has to be re-defined as well.
        sitemapWriter(XmlSitemapWriter) { bean ->
          bean.autowire = true
        }

      }

      beanDefinitions.registerBeans(event.ctx)
    }
  }

  def doWithWebDescriptor = { xml -> }
  def doWithDynamicMethods = { ctx -> }
  def doWithApplicationContext = { applicationContext -> }
  def onConfigChange = { event -> }

}
