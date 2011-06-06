package grails.plugins.sitemapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Kim A. Betti
 */
public abstract class AbstractSitemapWriter implements ApplicationContextAware {
	
	private Log log = LogFactory.getLog(AbstractSitemapWriter.class);
	
	protected SitemapServerUrlResolver serverUrlResolver;
	protected Set<SitemapSource> sitemapSources = new HashSet<SitemapSource>();

	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		Map<String, SitemapSource> sitemapBeanSources = ctx.getBeansOfType(SitemapSource.class);
		for (String beanName : sitemapBeanSources.keySet()) {
			log.debug("Detected Spring bean sitemap source: " + beanName);
			this.sitemapSources.add(sitemapBeanSources.get(beanName));
		}
	}
	
	public abstract void writeIndexEntries(ServletOutputStream outputStream) throws IOException;

  public void writeSitemapEntries(ServletOutputStream outputStream, String sourceName) throws IOException {
    writeSitemapEntries(outputStream, sourceName, 0);
  }

	public void writeSitemapEntries(ServletOutputStream outputStream, String sourceName, Integer pageNum) throws IOException {
		for (SitemapSource source : sitemapSources) {
			if (source.getSitemapName().equals(sourceName)) {
				writeSitemapEntries(outputStream, source, pageNum);
				return;
			}
		}
		
		throw new RuntimeException("Unable to find source with name " + sourceName);
	}
	
	public abstract void writeSitemapEntries(ServletOutputStream outputStream, SitemapSource source, Integer pageNum) throws IOException;
	
	public void setServerUrlResolver(SitemapServerUrlResolver serverUrlResolver) {
		this.serverUrlResolver = serverUrlResolver;
	}
	
}
