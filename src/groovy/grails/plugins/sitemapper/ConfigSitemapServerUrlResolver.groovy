package grails.plugins.sitemapper

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 * Default sitemapServerUrl bean.
 * Looks up server URL in application configuration (grails.serverURL). 
 * @author Kim A. Betti
 */
class ConfigSitemapServerUrlResolver implements SitemapServerUrlResolver {
	
	private static final Log log = LogFactory.getLog(this)
	
	public String getServerUrl() {
		String serverUrl = ConfigurationHolder.config?.grails?.serverURL?.toString()
		if (!serverUrl) {
			log.error ("Unable to find server url, please set grails.serverURL in Config.groovy, "
				+ "or provided your own implementation of SitemapServerUrlResolver.")
		}
    serverUrl
	}
	
}