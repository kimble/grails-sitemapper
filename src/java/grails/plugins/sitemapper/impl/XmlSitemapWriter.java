package grails.plugins.sitemapper.impl;

import grails.plugins.sitemapper.EntryWriter;
import grails.plugins.sitemapper.Sitemapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSitemapWriter extends AbstractSitemapWriter {

    private final static Logger log = LoggerFactory.getLogger(XmlSitemapWriter.class);

    private final static String SITEMAP_OPEN = "<sitemap>";
    private final static String SITEMAP_CLOSE = "</sitemap>\n";

    @Override
    public void writeIndexEntries(PrintWriter writer) throws IOException {
        writeIndexHead(writer);

        SitemapDateUtils dateUtils = new SitemapDateUtils();
        String serverUrl = serverUrlResolver.getServerUrl();
        for (String mapperName : sitemappers.keySet()) {
            Sitemapper mapper = sitemappers.get(mapperName);
            Date previousUpdate = mapper.getPreviousUpdate();
            if (previousUpdate != null) {
                String lastMod = dateUtils.formatForSitemap(previousUpdate);

                if (mapper instanceof PaginationSitemapper) {
                    PaginationSitemapper paginationMapper = (PaginationSitemapper) mapper;
                    int count = paginationMapper.getPagesCount();
                    for (int i = 0; i < count; i++) {
                        writeIndexExtry(writer, serverUrl, mapperName + "-" + i, lastMod);
                    }
                } else {
                    writeIndexExtry(writer, serverUrl, mapperName, lastMod);
                }
            } else {
                log.debug("No entries found for " + mapperName);
            }
        }

        writeIndexTail(writer);
    }

    @Override
    public void writeSitemapEntries(PrintWriter writer, Sitemapper sitemapper, Integer pageNumber) throws IOException {
        writeSitemapHead(writer);
        super.writeSitemapEntries(writer, sitemapper, pageNumber);
        writeSitemapTail(writer);
    }

    @Override
    public void writeSitemapEntries(PrintWriter writer, Sitemapper mapper) throws IOException {
        String serverUrl = serverUrlResolver.getServerUrl();
        EntryWriter entryWriter = new XmlEntryWriter(writer, serverUrl);
        mapper.withEntryWriter(entryWriter);
    }

    private void writeIndexHead(PrintWriter writer) {
        writer.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        writer.print("\n");
        writer.print("<sitemapindex xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");
        writer.print("\n");
    }

    private void writeIndexTail(PrintWriter writer) {
        writer.print("</sitemapindex>");
    }

    private void writeIndexExtry(PrintWriter writer, String serverUrl, String mapperName, String lastMod) throws IOException {
        writer.print(SITEMAP_OPEN);
        writer.print(String.format("<loc>%s/sitemap-%s.xml</loc>", serverUrl, mapperName));
        writer.print(String.format("<lastmod>%s</lastmod>", lastMod));
        writer.print(SITEMAP_CLOSE);
    }

    private void writeSitemapHead(PrintWriter writer) {
        writer.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        writer.print("\n");
        writer.print("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");
        writer.print("\n");
    }

    private void writeSitemapTail(PrintWriter writer) {
        writer.print("</urlset>");
    }

}