package grails.plugins.sitemapper;

import java.util.Date;
import java.util.Map;
import java.io.IOException;
import javax.servlet.ServletOutputStream;

/**
 * Responsible for printing sitemap 
 * entries as XML to output stream. 
 * @author Kim A. Betti
 */
final class XmlEntryWriter {
	
	private final SitemapDateUtils dateUtils;
	private final ServletOutputStream out;
	private final String serverUrl;
	
	private final static String URL_OPEN = "<url>";
	private final static String URL_CLOSE = "</url>\n";
	
	public final static String LOCATION_KEY = "location";
	public final static String LAST_MOD_KEY = "lastModification";
	public final static String CHANGE_FREQ_KEY = "changeFrequency";
	public final static String PRIORITY_KEY = "priority";
	
	public final static String LOCATION_TAG = "loc";
	public final static String LAST_MOD_TAG = "lastmod";
	public final static String CHANGE_FREQ_TAG = "changefreq";
	public final static String PRIORITY_TAG = "priority";
	
	public XmlEntryWriter(ServletOutputStream out, String serverUrl) {
		this.dateUtils = new SitemapDateUtils();
		this.serverUrl = serverUrl;
		this.out = out;
	}
	
	public void addEntry(final Map<String, String> args) throws IOException {
		out.print(URL_OPEN);
		printLocation(args);
		printLastModification(args);
		printChangeFrequency(args);
		printPriority(args);
		out.print(URL_CLOSE);
	}
	
	private void printLocation(final Map<String, String> args) throws IOException {
		if (!args.containsKey(LOCATION_KEY)) {
			throw new SitemapperException("Missing '" + LOCATION_KEY + "'");
		} else {
			final String locationUrl = serverUrl + args.get(LOCATION_KEY); 
			printTag(LOCATION_TAG, locationUrl);
		}
	}
	
	private void printLastModification(final Map<String, ?> args) throws IOException {
		if (!args.containsKey(LAST_MOD_KEY)) {
			throw new SitemapperException("Missing '" + LAST_MOD_KEY + "'");
		} else {
			final Date lastUpdate = (Date) args.get(LAST_MOD_KEY);
			final String timestamp = dateUtils.formatForSitemap(lastUpdate);
			printTag(LAST_MOD_TAG, timestamp);
		}
	}
	
	private void printChangeFrequency(final Map<String, String> args) throws IOException {
		if (args.containsKey(CHANGE_FREQ_KEY)) {
			printTag(CHANGE_FREQ_TAG, args.get(CHANGE_FREQ_KEY));
		}
	}
	
	private void printPriority(final Map<String, ?> args) throws IOException {
		if (args.containsKey(PRIORITY_KEY)) {
			final Object priority = args.get(PRIORITY_KEY);
			printTag(PRIORITY_TAG, priority.toString());
		}
	}
	
	private void printTag(final String tagName, final String value) throws IOException {
		final String markup = String.format("<%s>%s</%1$s>", tagName, value);
		out.print(markup);
	}
	
}