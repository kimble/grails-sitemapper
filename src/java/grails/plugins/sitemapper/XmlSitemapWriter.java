package grails.plugins.sitemapper;

import groovy.lang.Closure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * 
 * @author Kim A. Betti
 */
public class XmlSitemapWriter extends AbstractSitemapWriter {
    
    private final static Logger log = LoggerFactory.getLogger(XmlSitemapWriter.class);

    private final static String SITEMAP_OPEN = "<sitemap>";
    private final static String SITEMAP_CLOSE = "</sitemap>\n";

    @Override
    public void writeIndexEntries(ServletOutputStream out) throws IOException {
        SitemapDateUtils dateUtils = new SitemapDateUtils();
        String serverUrl = serverUrlResolver.getServerUrl();
        for (SitemapSource source : this.sitemapSources) {
            String mapperName = source.getSitemapName();
            Date modifiedAt = source.getPreviousUpdate();
            
            if (modifiedAt != null) {
                if (source instanceof PaginationSitemapSource) {
                  PaginationSitemapSource paginationSource = (PaginationSitemapSource) source;

                  int count = (int) Math.ceil((double)paginationSource.getTotalCount()
                      / paginationSource.getPerPageCount());

                  for (int i = 0 ; i < count; i ++) {
                    out.print(SITEMAP_OPEN);
                    out.print(String.format("<loc>%s/sitemap-%s.xml</loc>", serverUrl, mapperName + "-" + i));
                    out.print(String.format("<lastmod>%s</lastmod>", dateUtils.formatForSitemap(modifiedAt)));
                    out.print(SITEMAP_CLOSE);
                  }
                } else {
                  out.print(SITEMAP_OPEN);
                  out.print(String.format("<loc>%s/sitemap-%s.xml</loc>", serverUrl, mapperName));
                  out.print(String.format("<lastmod>%s</lastmod>", dateUtils.formatForSitemap(modifiedAt)));
                  out.print(SITEMAP_CLOSE);
                }
            } else {
                log.error("{} did not provied a recent modification date - not written to sitemap", mapperName);
            }
        }
    }

    @Override
    public void writeSitemapEntries(ServletOutputStream out, SitemapSource source, Integer pageNum)
        throws IOException {
        String serverUrl = serverUrlResolver.getServerUrl();
        XmlEntryWriter entryWriter = new XmlEntryWriter(out, serverUrl, pageNum);

        Closure mapper = source.getSitemapper();
        mapper.setResolveStrategy(Closure.DELEGATE_FIRST);
        mapper.setDelegate(entryWriter);
        mapper.call();
    }

}