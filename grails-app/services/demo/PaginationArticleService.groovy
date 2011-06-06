package demo

import grails.plugins.sitemapper.PaginationSitemapSource

/**
 * For testing / example only
 * @author Alexey M. Zhokhov
 */
class PaginationArticleService implements PaginationSitemapSource {

  static transactional = false

  final String sitemapName = "paginationArticles"
  final Date previousUpdate = new Date()

  final Integer perPageCount = 40
  final Long totalCount = 100

  final Closure sitemapper = {
    switch (pageNum) {
      case 0:
        1.times { n ->
			    addEntry (
				    location: '/page1-' + n + '.html',
				    lastModification: new Date(),
				    changeFrequency: 'monthly',
				    priority: 0.5
			    )
		    }
        break;

      case 1:
        2.times { n ->
			    addEntry (
				    location: '/page2-' + n + '.html',
				    lastModification: new Date(),
				    changeFrequency: 'monthly',
				    priority: 0.5
			    )
		    }
        break;

      case 2:
        3.times { n ->
			    addEntry (
				    location: '/page3-' + n + '.html',
				    lastModification: new Date(),
				    changeFrequency: 'monthly',
				    priority: 0.5
			    )
		    }
        break;
    }
	}
}
