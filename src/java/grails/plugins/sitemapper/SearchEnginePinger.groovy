package grails.plugins.sitemapper

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.HttpParams;
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * 
 * @author Kim A. Betti
 */
class SearchEnginePinger implements InitializingBean {
	
	private final static log = LogFactory.getLog(this)
	
	SitemapServerUrlResolver sitemapServerUrlResolver
	Integer pingTimeoutInSeconds = 8
	
	HttpClient httpClient
	HttpParams httpParams
	
	// Search engine name => url format with %s for sitemap
	Map<String, String> searchEnginePingUrls = [:]
		
	public boolean pingAll() {
		boolean allSuccess = true
		String sitemapUrl = sitemapServerUrlResolver.serverUrl + "/sitemap.xml"
		searchEnginePingUrls.each { name, urlFormat ->
			if (!pingSearchEngine(name, urlFormat, sitemapUrl)) {
				allSuccess = false
			}
		}
		
		return allSuccess
	}
	
	private boolean pingSearchEngine(String engineName, String urlFormat, String sitemapUrl) {
		log.debug "Pinging $engineName @ $urlFormat"
		try {
			String pingUrl = String.format(urlFormat, sitemapUrl)
			HttpGet httpGet = new HttpGet(pingUrl)
			HttpResponse response = httpClient.execute(httpGet)
			log.info "Pinged $engineName"
			return true
		} catch (HttpResponseException hex) {
			log.warn ("Unable to ping $engineName. Http response exception, "
				+ "${hex.statusCode}, message: ${hex.message}", hex)
		} catch (Exception ex) {
			log.error "Unable to ping $engineName, ex: " + ex.message, ex
		}
		
		return false
	}
	
	public void afterPropertiesSet() {
		initializeHttpParams();
	}
	
	private void initializeHttpParams() {
		httpParams = httpClient.getParams()
		HttpConnectionParams.setConnectionTimeout(httpParams, pingTimeoutInSeconds * 1000)
		HttpConnectionParams.setSoTimeout(httpParams, pingTimeoutInSeconds * 1000);
	}
	
	public void setSearchEnginePingUrls(Map<String, String> urls) {
		urls.each { name, urlFormat ->
			addSearchEnginePingUrl(name, urlFormat)
		}
	}
	
	public void addSearchEnginePingUrl(String engineName, String urlFormat) {
		log.debug "Adding ping url for $engineName -> $urlFormat"
		searchEnginePingUrls[engineName] = urlFormat
	}
	
}
