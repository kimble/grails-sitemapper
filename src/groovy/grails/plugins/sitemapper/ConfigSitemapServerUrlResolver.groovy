package grails.plugins.sitemapper

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.grails.commons.ConfigurationHolder;
import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware;
import org.springframework.beans.factory.InitializingBean;

/**
 * Default sitemapServerUrl bean.
 * Looks up server URL in application configuration (grails.serverURL). 
 * @author Kim A. Betti
 */
class ConfigSitemapServerUrlResolver implements SitemapServerUrlResolver, InitializingBean, GrailsApplicationAware {
	
	private static final Log log = LogFactory.getLog(this)
	
    GrailsApplication grailsApplication
	String serverUrl = null
	
	void afterPropertiesSet() {
        serverUrl = grailsApplication.config?.grails?.serverURL?.toString()
		if (!serverUrl) {
			log.error ("Unable to find server url, please set grails.serverURL in Config.groovy, "
				+ "or provided your own implementation of SitemapServerUrlResolver.")
		} else {
			setServerUrl(serverUrl)
		}
	}
	
	public void setServerUrl(String serverUrl) {
		if (serverUrl.endsWith("/")) {
			serverUrl = serverUrl.substring(0, serverUrl.length()-1)
		}
		
		this.serverUrl = serverUrl;
		log.info "Using " + serverUrl + " as server url"
	}
	
}