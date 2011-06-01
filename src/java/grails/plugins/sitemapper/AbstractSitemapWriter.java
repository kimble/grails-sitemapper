package grails.plugins.sitemapper;

import grails.plugins.sitemapper.artefact.SitemapperArtefactHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

/**
 * 
 * @author Kim A. Betti
 */
public abstract class AbstractSitemapWriter {

    private Logger log = LoggerFactory.getLogger(AbstractSitemapWriter.class);

    protected SitemapServerUrlResolver serverUrlResolver;
    protected Map<String, Sitemapper> sitemappers = new HashMap<String, Sitemapper>();

    public abstract void writeIndexEntries(ServletOutputStream outputStream) throws IOException;

    public void writeSitemapEntries(ServletOutputStream outputStream, String sourceName) throws IOException {
        Sitemapper mapper = getMapperByName(sourceName);
        writeSitemapEntries(outputStream, mapper);
    }

    protected Sitemapper getMapperByName(String name) {
        String mapperName = name.toLowerCase();
        Sitemapper mapper = sitemappers.get(mapperName);
        if (mapper == null) {
            throw new RuntimeException("Unable to find source with name " + name);
        } else {
            return mapper;
        }
    }

    public abstract void writeSitemapEntries(ServletOutputStream os, Sitemapper m) throws IOException;

    @Required @Autowired
    public void setSitemappers(Set<Sitemapper> newMappers) {
        log.info("Setting {} sitemapper(s)", newMappers.size());
        this.sitemappers.clear();
        for (Sitemapper mapper : newMappers) {
            String mapperName = getMapperName(mapper.getClass());
            log.info("Adding sitemapper {}", mapperName);
            this.sitemappers.put(mapperName, mapper);
        }
    }
    
    protected String getMapperName(Class<? extends Sitemapper> sitemapperClass) {
        String className = sitemapperClass.getSimpleName();
        assert className.endsWith(SitemapperArtefactHandler.SUFFIX);
        
        int endIndex = className.length() - SitemapperArtefactHandler.SUFFIX.length();
        return className.substring(0, endIndex).toLowerCase();
    }

    @Required
    public void setServerUrlResolver(SitemapServerUrlResolver serverUrlResolver) {
        this.serverUrlResolver = serverUrlResolver;
    }

}
