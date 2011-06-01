package demo

import grails.plugins.sitemapper.Sitemapper;

/**
 * For testing / example only
 * @author Kim A. Betti
 */
class ArticleService implements Sitemapper {

    static transactional = false
	
	final String sitemapName = "articles"
	final Date previousUpdate = new Date()
	final Closure sitemapper = { 
		3.times { n ->
			addEntry (
				location: '/test-' + n + '.html', 
				lastModification: new Date(),
				changeFrequency: 'monthly',
				priority: 0.5
			)
		}
	}
	
}