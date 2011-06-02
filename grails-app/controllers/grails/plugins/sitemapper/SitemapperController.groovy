package grails.plugins.sitemapper

import grails.plugins.sitemapper.impl.XmlSitemapWriter;

import javax.servlet.ServletOutputStream;

/**
 * 
 * @author Kim A. Betti
 */
class SitemapperController {
	
	XmlSitemapWriter sitemapWriter

	// The index sitemap
    def index = {
		response.contentType = "application/xml"
		ServletOutputStream out = response.outputStream
		writeIndexHead out
		sitemapWriter.writeIndexEntries out
		writeIndexTail out
		out.flush()
	}
	
	private void writeIndexHead(ServletOutputStream out) {
		out << '<?xml version="1.0" encoding="UTF-8"?>' << "\n"
		out << '<sitemapindex xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">' << "\n"
	}
	
	private void writeIndexTail(ServletOutputStream out) {
		out << '</sitemapindex>'
	}
	
	// The actual sitemaps / urlsets
	def source = {
		response.contentType = "application/xml"
		ServletOutputStream out = response.outputStream
		writeSitemapHead out
		sitemapWriter.writeSitemapEntries out, getSourceName(params.name)
		writeSitemapTail out
		out.flush()
	}
	
	private String getSourceName(String input) {
		if (!input)
			throw new Exception("Missing source name");
		
		String name = input
		if (name.indexOf('.') > 0)
			name = name.substring(0, name.indexOf('.'))
			
		return name
	}
	
	private void writeSitemapHead(ServletOutputStream out) {
		out << '<?xml version="1.0" encoding="UTF-8"?>' << "\n"
		out << '<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">' << "\n"
	}
	
	private void writeSitemapTail(ServletOutputStream out) {
		out << '</urlset>'
	}
	
}
