import grails.plugins.sitemapper.*
import grails.plugins.sitemapper.artefact.SitemapperArtefactHandler
import grails.plugins.sitemapper.artefact.SitemapperClass

import org.apache.http.impl.client.DefaultHttpClient
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class SitemapperGrailsPlugin {
	
    def version = "0.4"
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

	def artefacts = [ SitemapperArtefactHandler ]
	
	def watchedResources = [
		"file:./grails-app/sitemapper/**/*Sitemapper.groovy",
		"file:./plugins/*/grails-app/sitemapper/**/*Sitemapper.groovy"
	]

    def doWithSpring = {
		
		application.sitemapperClasses.each { SitemapperClass sc ->
			println "Registering sitemapper class " + sc.name + " as sitemapper / bean"
			"${sc.name}Sitemapper"(sc.clazz) { bean ->
				bean.autowire = "byName"
			}
		}
        
        sitemapServerUrlResolver(ConfigSitemapServerUrlResolver)
		sitemapWriter(XmlSitemapWriter) { 
			serverUrlResolver = ref("sitemapServerUrlResolver")
		}
		
		searchEnginePinger(SearchEnginePinger) {
			searchEnginePingUrls = ConfigurationHolder.config?.searchEnginePingUrls ?: [:]
			sitemapServerUrlResolver = ref("sitemapServerUrlResolver")
			httpClient = new DefaultHttpClient()
		}
    }

	def doWithWebDescriptor = { xml -> }
    def doWithDynamicMethods = { ctx -> }
    def doWithApplicationContext = { applicationContext -> }
    def onChange = { event -> }
    def onConfigChange = { event -> }
	
}