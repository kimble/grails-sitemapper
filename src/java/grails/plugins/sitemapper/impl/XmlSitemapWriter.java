package grails.plugins.sitemapper.impl;

import grails.plugins.sitemapper.EntryWriter;
import grails.plugins.sitemapper.Sitemapper;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

public class XmlSitemapWriter extends AbstractSitemapWriter {

  private final static String SITEMAP_OPEN = "<sitemap>";
  private final static String SITEMAP_CLOSE = "</sitemap>\n";

  @Override
  public void writeIndexEntries(ServletOutputStream out) throws IOException {
    SitemapDateUtils dateUtils = new SitemapDateUtils();
    String serverUrl = serverUrlResolver.getServerUrl();
    for (String mapperName : sitemappers.keySet()) {
      Sitemapper mapper = sitemappers.get(mapperName);
      String lastMod = dateUtils.formatForSitemap(mapper.getPreviousUpdate());

      if (mapper instanceof PaginationSitemapper) {
        PaginationSitemapper paginationMapper = (PaginationSitemapper) mapper;

        int count = paginationMapper.getPagesCount();

        for (int i = 0; i < count; i++) {
          writeIndexExtry(out, serverUrl, mapperName + "-" + i, lastMod);
        }
      } else {
        writeIndexExtry(out, serverUrl, mapperName, lastMod);
      }
    }
  }

  @Override
  public void writeSitemapEntries(ServletOutputStream out, Sitemapper mapper) throws IOException {
    String serverUrl = serverUrlResolver.getServerUrl();
    EntryWriter entryWriter = new XmlEntryWriter(out, serverUrl);
    mapper.withEntryWriter(entryWriter);
  }

  private void writeIndexExtry(ServletOutputStream out, String serverUrl, String mapperName, String lastMod)
      throws IOException {
    out.print(SITEMAP_OPEN);
    out.print(String.format("<loc>%s/sitemap-%s.xml</loc>", serverUrl, mapperName));
    out.print(String.format("<lastmod>%s</lastmod>", lastMod));
    out.print(SITEMAP_CLOSE);
  }

}