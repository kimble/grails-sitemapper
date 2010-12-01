class SitemapperUrlMappings {

	static mappings = {
		"/sitemap" (controller: 'sitemapper')
		"/sitemap-$name" (controller: 'sitemapper', action: 'source')
		
	}
	
}
