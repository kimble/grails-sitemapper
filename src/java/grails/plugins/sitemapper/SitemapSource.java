package grails.plugins.sitemapper;

import groovy.lang.Closure;
import java.util.Date;

/**
 * 
 * @author Kim A. Betti
 */
public interface SitemapSource {

	String getSitemapName();
	
	Date getPreviousUpdate();
	
	Closure getSitemapper();
	
}