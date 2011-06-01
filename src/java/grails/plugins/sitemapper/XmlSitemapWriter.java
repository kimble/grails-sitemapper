package grails.plugins.sitemapper;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

/**
 * 
 * @author Kim A. Betti
 */
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
			
			out.print(SITEMAP_OPEN);
			out.print(String.format("<loc>%s/sitemap-%s.xml</loc>", serverUrl, mapperName));
			out.print(String.format("<lastmod>%s</lastmod>", lastMod));
			out.print(SITEMAP_CLOSE);
		}
	}

	@Override
	public void writeSitemapEntries(ServletOutputStream out, Sitemapper mapper) throws IOException {
		String serverUrl = serverUrlResolver.getServerUrl();
		EntryWriter entryWriter = new XmlEntryWriter(out, serverUrl);
		mapper.withEntryWriter(entryWriter);
	}
	
}