package grails.plugins.sitemapper.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SimpleDateFormat is not thread-safe
 * @author Kim A. Betti
 */
public class SitemapDateUtils {
	
	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mmZ";
	
	private final SimpleDateFormat dateFormat;

	public SitemapDateUtils() {
		dateFormat = new SimpleDateFormat(DATE_FORMAT);
	}

	public String formatForSitemap(final Date date) {
		final String formatted = dateFormat.format(date);
		final String postfix = formatted.substring(formatted.length() - 2); // Hack for timezone format
		return formatted.substring(0, formatted.length() - 2) + ":" + postfix; 
	}
	
}