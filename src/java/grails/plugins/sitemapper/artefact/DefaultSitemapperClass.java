package grails.plugins.sitemapper.artefact;

import org.codehaus.groovy.grails.commons.AbstractGrailsClass;

public class DefaultSitemapperClass extends AbstractGrailsClass implements SitemapperClass {

	@SuppressWarnings("rawtypes")
	public DefaultSitemapperClass(Class clazz) {
        super(clazz, SitemapperArtefactHandler.SUFFIX);
    }
	
}