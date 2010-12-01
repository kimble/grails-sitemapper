package grails.plugins.sitemapper;

/**
 * 
 * @author Kim A. Betti
 */
@SuppressWarnings("serial")
public class SitemapperException extends RuntimeException {

	public SitemapperException(String message, Throwable cause) {
		super(message, cause);
	}

	public SitemapperException(String message) {
		super(message);
	}

	public SitemapperException(Throwable cause) {
		super(cause);
	}

}