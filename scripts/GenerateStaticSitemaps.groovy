includeTargets << grailsScript("Init")

includeTargets << grailsScript("Compile")
includeTargets << grailsScript("_GrailsBootstrap")
includeTargets << grailsScript("_GrailsCreateArtifacts")

target(main: "Generate sitemaps files") {
  depends(compile, parseArguments, configureProxy, packageApp, classpath, loadApp, configureApp)

  // loading classes
  def paginationSitemapperClass = classLoader.loadClass("grails.plugins.sitemapper.impl.PaginationSitemapper", true)

  def sitemapWriter = appCtx.getBean("sitemapWriter")

  println "Generating sitemap index file ..."

  def indexWriter = new PrintWriter(new File("target/sitemap.xml"))
  sitemapWriter.writeIndexEntries(indexWriter)
  indexWriter.flush()

  for (String mapperName: sitemapWriter.getSitemappers().keySet()) {
    def mapper = sitemapWriter.getSitemappers().get(mapperName);

    if (paginationSitemapperClass.isInstance(mapper)) {
      for (int i = 0; i < mapper.pagesCount; i++) {
        generateUrlSetFile(sitemapWriter, mapper, i, "sitemap-${mapperName}-${i}.xml")
      }
    } else {
      generateUrlSetFile(sitemapWriter, mapper, 0, "sitemap-${mapperName}.xml")
    }
  }
}

private void generateUrlSetFile(sitemapWriter, mapper, part, fileName) {
  println "Generating ${fileName} ..."

  PrintWriter urlsetWriter = new PrintWriter(new File("target/${fileName}"))
  sitemapWriter.writeSitemapEntries(urlsetWriter, mapper, part);
  urlsetWriter.flush()
}

setDefaultTarget(main)
