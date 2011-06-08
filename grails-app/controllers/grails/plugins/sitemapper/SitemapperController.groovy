package grails.plugins.sitemapper

import grails.plugins.sitemapper.impl.XmlSitemapWriter;

import javax.servlet.ServletOutputStream;

import org.springframework.util.Assert;

/**
 * Generates sitemaps on the fly. The XML output is piped directly to 
 * response.output to avoid unnecessary object generation and memory usage. 
 * The drawback is that exceptions can stop the sitemap generation 
 * "mid stream" so test your sitemappers.  
 * @author Kim A. Betti
 */
class SitemapperController {
	
	XmlSitemapWriter sitemapWriter

	/**
	 *  The index sitemap
	 *  ------------------
	 *  Contains uri's to each sitemapper sitemap 
	 *  + a time stamp indicating the last update. 
	 */ 
    def index = {
		response.contentType = "application/xml"
		ServletOutputStream out = response.outputStream
		writeIndexHead(out)
		sitemapWriter.writeIndexEntries(out)
		writeIndexTail(out)
		out.flush()
	}
	
	private void writeIndexHead(OutputStream out) {
		out << '<?xml version="1.0" encoding="UTF-8"?>' << "\n"
		out << '<sitemapindex xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">' << "\n"
	}
	
	private void writeIndexTail(OutputStream out) {
		out << '</sitemapindex>'
	}
	
	/**
	 * Sitemapper sitemap
	 * ------------------
	 * Each sitemapper implementation will have its own sitemap file.
	 * The entries in this file is populated by calling the mapper. 
	 */
	def source = {
		response.contentType = "application/xml"
		ServletOutputStream out = response.outputStream
		writeSitemapHead(out)
		sitemapWriter.writeSitemapEntries(out, removeEnding(params.name))
		writeSitemapTail(out)
		out.flush()
	}
	
	private String removeEnding(String mapperName) {
        Assert.hasLength(mapperName)
        int dotIndex = mapperName.indexOf('.')
        return dotIndex > 0 ? mapperName.substring(0, dotIndex) : mapperName
	}
	
	private void writeSitemapHead(OutputStream out) {
		out << """<?xml version="1.0" encoding="UTF-8"?>\n"""
		out << """<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">\n"""
	}
	
	private void writeSitemapTail(OutputStream out) {
		out << '</urlset>'
	}
	
}
