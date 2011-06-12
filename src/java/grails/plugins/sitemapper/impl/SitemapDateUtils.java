package grails.plugins.sitemapper.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SimpleDateFormat is not thread-safe, neither is this class.
 *
 * @author Kim A. Betti
 */
public class SitemapDateUtils {

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");

  public String formatForSitemap(final Date date) {
    final String formatted = dateFormat.format(date);
    final String postfix = formatted.substring(formatted.length() - 2); // Hack for timezone format
    return formatted.substring(0, formatted.length() - 2) + ":" + postfix;
  }

}