package grails.plugins.sitemapper;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletOutputStream;

/**
 * Responsible for printing sitemap 
 * entries as XML to output stream. 
 * @author Kim A. Betti
 */
final class XmlEntryWriter implements EntryWriter {
	
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
	
	@Override
    public void addEntry(String location, Date modifiedAt) throws IOException {
	    out.print(URL_OPEN);
        printLocation(location);
        printLastModification(modifiedAt);
        out.print(URL_CLOSE);
    }

    @Override
    public void addEntry(String location, Date modifiedAt, String changeFrequency, int priority) throws IOException {
        out.print(URL_OPEN);
        printLocation(location);
        printLastModification(modifiedAt);
        printChangeFrequency(changeFrequency);
        printPriority(priority);
        out.print(URL_CLOSE);
    }
    
    private void printLocation(String locationUrl) throws IOException {
        printTag(LOCATION_TAG, serverUrl + locationUrl);
    }
	
	private void printLastModification(Date modifiedAt) throws IOException {
        printTag(LAST_MOD_TAG, dateUtils.formatForSitemap(modifiedAt));
	}
	
	private void printChangeFrequency(String changeFrequency) throws IOException {
	    printTag(CHANGE_FREQ_TAG, changeFrequency);
	}

	private void printPriority(int priority) throws IOException {
	    printTag(PRIORITY_TAG, priority + "");
	}
	
	private void printTag(final String tagName, final String value) throws IOException {
		out.print(String.format("<%s>%s</%1$s>", tagName, value));
	}
	
}