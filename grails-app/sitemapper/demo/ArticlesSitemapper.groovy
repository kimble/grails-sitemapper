package demo

import grails.plugins.sitemapper.EntryWriter
import grails.plugins.sitemapper.impl.PaginationSitemapper

/**
 * For testing / example only
 * @author Alexey M. Zhokhov
 */
class ArticlesSitemapper extends PaginationSitemapper {

  Date previousUpdate = new Date()

  final Integer perPageCount = 40
  final Long totalCount = 100

  @Override
  public void withEntryWriter(EntryWriter entryWriter) {
    switch (pageNumber) {
      case 0:
        1.times { n ->
          entryWriter.addEntry "/articles/${n}", new Date() - 1
        }
        break;

      case 1:
        2.times { n ->
          entryWriter.addEntry "/articles/${n}", new Date(), "monthly", 0.5
        }
        break;

      case 2:
        3.times { n ->
          entryWriter.addEntry "/articles/${n}", new Date(), "daily", 0.6
        }
        break;
    }
  }

}
