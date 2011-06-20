package grails.plugins.sitemapper

import grails.plugin.spock.UnitSpec

import org.codehaus.groovy.grails.commons.GrailsApplication

class ConfigSitemapServerUrlResolverSpec extends UnitSpec {

    def "Should fail early if no server url has been configured"() {
        given:
        GrailsApplication appMock = Mock(GrailsApplication)
        ConfigSitemapServerUrlResolver urlResolver = new ConfigSitemapServerUrlResolver(grailsApplication: appMock)
        
        when:
        urlResolver.getServerUrl()
        
        then:
        1 * appMock.getConfig() >> null
        
        and:
        SitemapperException ex = thrown()
    }
    
    def "Should be able to remove trailing slash from url"() {
        given:
        ConfigSitemapServerUrlResolver urlResolver = new ConfigSitemapServerUrlResolver()
        
        expect:
        urlResolver.removeTrailingSlash("http://developer-b.com") == "http://developer-b.com"
        urlResolver.removeTrailingSlash("http://developer-b.com/") == "http://developer-b.com"
    }
    
}
