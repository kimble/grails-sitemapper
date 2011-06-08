package grails.plugins.sitemapper.impl;

import static grails.plugins.sitemapper.artefact.SitemapperArtefactHandler.SUFFIX;

import grails.plugins.sitemapper.SitemapServerUrlResolver;
import grails.plugins.sitemapper.Sitemapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

public abstract class AbstractSitemapWriter {

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
        } 

        return mapper;
    }

    public abstract void writeSitemapEntries(ServletOutputStream os, Sitemapper m) throws IOException;

    @Required @Autowired
    public void setSitemappers(Set<Sitemapper> newMappers) {
        this.sitemappers.clear();
        for (Sitemapper mapper : newMappers) {
            String mapperName = getMapperName(mapper.getClass());
            this.sitemappers.put(mapperName, mapper);
        }
    }
    
    protected String getMapperName(Class<? extends Sitemapper> sitemapperClass) {
        String className = sitemapperClass.getSimpleName();
        Assert.isTrue(className.endsWith(SUFFIX));
        int endIndex = className.length() - SUFFIX.length();
        return className.substring(0, endIndex).toLowerCase();
    }

    @Required
    public void setSitemapServerUrlResolver(SitemapServerUrlResolver serverUrlResolver) {
        this.serverUrlResolver = serverUrlResolver;
    }

}
