defaultDestPath = "target/sitemaps"

siteMapCreate = { String destPath = defaultDestPath ->
  ant.mkdir dir: destPath

  // loading classes
  def paginationSitemapperClass = classLoader.loadClass("grails.plugins.sitemapper.impl.PaginationSitemapper", true)

  def sitemapWriter = appCtx.getBean("sitemapWriter")

  generateIndexFile(sitemapWriter, destPath)

  for (String mapperName: sitemapWriter.getSitemappers().keySet()) {
    def mapper = sitemapWriter.getSitemappers().get(mapperName);

    if (paginationSitemapperClass.isInstance(mapper)) {
      for (int i = 0; i < mapper.pagesCount; i++) {
        generateUrlSetFile(sitemapWriter, mapper, i, "sitemap-${mapperName}-${i}.xml", destPath)
      }
    } else {
      generateUrlSetFile(sitemapWriter, mapper, 0, "sitemap-${mapperName}.xml", destPath)
    }
  }
}

generateIndexFile = { sitemapWriter, String destPath = defaultDestPath ->
  ant.echo "Generating sitemap index file ..."

  def indexWriter = new PrintWriter(new File("${destPath}/sitemap.xml"))
  sitemapWriter.writeIndexEntries(indexWriter)
  indexWriter.flush()
}

generateUrlSetFile = {sitemapWriter, mapper, part, fileName, String destPath = defaultDestPath ->
  ant.echo "Generating ${fileName} ..."

  PrintWriter urlsetWriter = new PrintWriter(new File("${destPath}/${fileName}"))
  sitemapWriter.writeSitemapEntries(urlsetWriter, mapper, part);
  urlsetWriter.flush()
}