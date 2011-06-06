package grails.plugins.sitemapper;

/**
 *
 * @author Alexey M. Zhokhov
 */
public interface PaginationSitemapSource extends SitemapSource {

  Integer getPerPageCount();

  Long getTotalCount();

}
