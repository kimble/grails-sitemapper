includeTargets << grailsScript("Init")

includeTargets << grailsScript("Compile")
includeTargets << grailsScript("_GrailsBootstrap")
includeTargets << grailsScript("_GrailsCreateArtifacts")
includeTargets << new File("${sitemapperPluginDir}/scripts/_SitemapCreate.groovy")

target(main: "Generate sitemaps files") {
  depends(compile, parseArguments, configureProxy, packageApp, classpath, loadApp, configureApp)

  siteMapCreate()
}

setDefaultTarget(main)
