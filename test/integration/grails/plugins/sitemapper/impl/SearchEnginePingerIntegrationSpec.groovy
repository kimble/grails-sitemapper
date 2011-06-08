package grails.plugins.sitemapper.impl

import grails.plugin.spock.IntegrationSpec
import kim.spock.httpmock.*

@WithHttpServer(port=23456)
class SearchEnginePingerIntegrationSpec extends IntegrationSpec {

	SearchEnginePinger searchEnginePinger
	
	def "pinging dummy search engine works"() {
		
		given: "mocked http server"
			TestHttpServer.mock = Mock(HttpServer)
		
		and: "register url for a dummy service to ping"
			String pingUri = "http://localhost:23456/searchEngine/ping?sitemap=%s"
			searchEnginePinger.addSearchEnginePingUrl "dummy", pingUri
			
		when: "ping all registered services"
			boolean allSuccess = searchEnginePinger.pingAll()
			
		then: "expect one call to the registered service, with the specified sitemap location"
			1 * TestHttpServer.mock.request("get", "/searchEngine/ping", 
				{ it["sitemap"] == "http://localhost:8080/sitemap.xml" }, _ ) >> "ok"
			
		and: "it should return true if every ping returned http 200"
			allSuccess == true
		
	}
	
}