class SitemapperUrlMappings {

	static mappings = {
		"/sitemap.xml" (controller: 'sitemapper')
		"/sitemap-$name" (controller: 'sitemapper', action: 'source')
		
	}
	
}
