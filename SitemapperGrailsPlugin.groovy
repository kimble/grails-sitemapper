import grails.plugins.sitemapper.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.apache.http.impl.client.DefaultHttpClient

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


    def doWithSpring = {
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