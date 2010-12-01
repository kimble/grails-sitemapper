Sitemapper for Grails applications
==================================

Create sitemaps on the fly. 

About sitemaps
--------------

Sitemaps allows search engines to quickly spot changes on your site without crawling the whole page. Note that search engines are obviously not compelled to index your sites faster if you add a sitemap, but chances are that a lot of them will. Have a look at [sitemaps.org](http://sitemaps.org) for more information about sitemaps. 

Search engine ping
------------------

Add something like this to your `Config.groovy` file. The %s will be substituted with the location of your index sitemap. 

    sitemapConsumers {
        bing 'http://www.bing.com/webmaster/ping.aspx?siteMap=%s'
        google 'http://www.google.com/webmasters/sitemaps/ping?sitemap=%s'
    }


Setup
-----------

The plugin will on startup register all Spring beans implementing `grails.plugins.sitemapper.SitemapSource`. These Spring beans will be invoked upon sitemap generation.

    class ArticleSitemapSource implements SitemapSource {

        // Each SitemapSource will result in a 
        // sitemap by the specified name
        String sitemapName = "articles"
        
        // Will be used for lastmod in 
        // the sitemap index.
        Date previousUpdate = new Date()
        
        // Invoke addEntry from this method for
        // each document you want to add to the sitemap.
        // location and lastModification is mandatory, you
        // can leave out changeFrequency and priority. 
        Closure sitemapper = {
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

Bugs / roadmap
--------------

 * Implement support for search engine ping. 