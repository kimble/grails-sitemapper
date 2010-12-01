package grails.plugins.sitemapper;

import groovy.lang.Closure;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletOutputStream;

/**
 * 
 * @author Kim A. Betti
 */
public class XmlSitemapWriter extends AbstractSitemapWriter {
	
	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mmZ";
	
	private final static String SITEMAP_OPEN = "<sitemap>";
	private final static String SITEMAP_CLOSE = "</sitemap>\n";

	@Override
	public void writeIndexEntries(ServletOutputStream out) throws IOException {
		SimpleDateFormat formater = new SimpleDateFormat(XmlSitemapWriter.DATE_FORMAT);
		String serverUrl = serverUrlResolver.getServerUrl();
		for (SitemapSource source : this.sitemapSources) {
			String mapperName = source.getSitemapName();
			String lastMod = formater.format(source.getPreviousUpdate());
			
			out.print(SITEMAP_OPEN);
			out.print(String.format("<loc>%s/sitemap-%s.xml</loc>", serverUrl, mapperName));
			out.print(String.format("<lastmod>%s</lastmod>", lastMod));
			out.print(SITEMAP_CLOSE);
		}
	}

	@Override
	public void writeSitemapEntries(ServletOutputStream out, SitemapSource source) throws IOException {
		String serverUrl = serverUrlResolver.getServerUrl();
		XmlEntryWriter entryWriter = new XmlEntryWriter(out, serverUrl);
		
		Closure mapper = source.getSitemapper();
		mapper.setResolveStrategy(Closure.DELEGATE_ONLY);
		mapper.setDelegate(entryWriter);
		mapper.call();
	}
	
}