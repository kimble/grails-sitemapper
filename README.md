Sitemapper for Grails applications
==================================

Create sitemaps on the fly. 

About sitemaps
--------------

Sitemaps allows search engines to quickly spot changes on your site without crawling the whole page. Note that search engines are obviously not compelled to index your sites faster if you add a sitemap, but chances are that a lot of them will. Have a look at [sitemaps.org](http://sitemaps.org) for more information about sitemaps. 

Search engine ping
------------------

Add something like this to your `Config.groovy` file. The %s will be substituted with sitemap uri. 

    sitemapConsumers {
        bing 'http://www.bing.com/webmaster/ping.aspx?siteMap=%s'
        google 'http://www.google.com/webmasters/sitemaps/ping?sitemap=%s'
    }

Important! This has not yet been fully implemented. 

Setup
-----------

Add your sitemapper artefacts as `grails-app/sitemapper/your/package/mapperNameSitemapper.groovy`. Each class has to implement the `grails.plugins.sitemapper.Sitemapper` interface. The `withEntryWriter` method will be invoked each time the sitemap is requested.

    import grails.plugins.sitemapper.Sitemapper

    class ForumSitemapper implements Sitemapper {
        
        @Override
        public Date getPreviousUpdate() {
            return new Date(); // .. 
        }
        	
    	@Override
    	public void withEntryWriter(EntryWriter entryWriter) {
            entryWriter.addEntry("/forum/entry/test", new Date() - 1)
            entryWriter.addEntry("/forum/entry/test-2", new Date(), "MONTHLY", 3)
    	}
    	
    }

Bugs / roadmap
--------------

 1. Implement support for search engine ping - in (slow) progress.

