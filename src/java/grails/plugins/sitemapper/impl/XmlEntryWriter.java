package grails.plugins.sitemapper.impl;

import grails.plugins.sitemapper.ContentChangeFrequency;
import grails.plugins.sitemapper.EntryWriter;
import grails.plugins.sitemapper.SitemapperException;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;


/**
 * Responsible for printing sitemap entries as XML to output stream.
 * @author Kim A. Betti
 */
final class XmlEntryWriter implements EntryWriter {

    private final SitemapDateUtils dateUtils;
    private final Appendable output;
    private final String serverUrl;

    private final static String URL_OPEN = "<url>";
    private final static String URL_CLOSE = "</url>\n";

    public final static String LOCATION_TAG = "loc";
    public final static String LAST_MOD_TAG = "lastmod";
    public final static String CHANGE_FREQ_TAG = "changefreq";
    public final static String PRIORITY_TAG = "priority";

    public XmlEntryWriter(Appendable output, String serverUrl) {
        this.dateUtils = new SitemapDateUtils();
        this.serverUrl = serverUrl;
        this.output = output;
    }

    @Override
    public void addEntry(String location, Date modifiedAt) throws IOException {
        output.append(URL_OPEN);
        printLocation(location);
        printLastModification(modifiedAt);
        output.append(URL_CLOSE);
    }

    @Override
    public void addEntry(String location, Date modifiedAt, ContentChangeFrequency changeFrequency, double priority) throws IOException {
        output.append(URL_OPEN);
        printLocation(location);
        printLastModification(modifiedAt);
        printChangeFrequency(changeFrequency.toString().toLowerCase());
        printPriority(priority);
        output.append(URL_CLOSE);
    }

    protected void printLocation(String locationUrl) throws IOException {
        printTag(LOCATION_TAG, serverUrl + locationUrl);
    }

    protected void printLastModification(Date modifiedAt) throws IOException {
        printTag(LAST_MOD_TAG, dateUtils.formatForSitemap(modifiedAt));
    }

    protected void printChangeFrequency(String changeFrequency) throws IOException {
        printTag(CHANGE_FREQ_TAG, changeFrequency);
    }

    protected void printPriority(double priority) throws IOException {
        if (priority < 0 || priority > 1) {
            throw new SitemapperException("Priority has to be between 0 and 1, not " + priority);
        }

        printTag(PRIORITY_TAG, priority + "");
    }

    protected void printTag(final String tagName, final String value) throws IOException {
        String escapedValue = StringEscapeUtils.escapeXml(value);
        String xml = String.format("<%s>%s</%1$s>", tagName, escapedValue);
        output.append(xml);
    }

}