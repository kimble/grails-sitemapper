package grails.plugins.sitemapper

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware

/**
 * Default sitemapServerUrl bean.
 * Looks up server URL in application configuration (grails.serverURL). 
 * @author Kim A. Betti
 */
class ConfigSitemapServerUrlResolver implements SitemapServerUrlResolver, GrailsApplicationAware {

    GrailsApplication grailsApplication

    public String getServerUrl() {
        String serverUrl = getServerUrlFromConfiguration()
        if (serverUrl == null) {
            throw new SitemapperException("Unable to find server url, please set grails.serverURL "
                    + "in Config.groovy, or provided your own implementation of SitemapServerUrlResolver.")
        }
        
        return removeTrailingSlash(serverUrl)
    }
    
    protected String getServerUrlFromConfiguration() {
        grailsApplication.config?.grails?.serverURL?.toString()
    }
    
    protected String removeTrailingSlash(String serverUrl) {
        serverUrl.endsWith("/") ? serverUrl.substring(0, serverUrl.size() - 1) : serverUrl
    }
    
}