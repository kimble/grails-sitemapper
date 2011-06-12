package grails.plugins.sitemapper;

import java.util.Date;

/**
 * Sitemapper artefacts have to implement this interface.
 *
 * @author Kim A. Betti
 */
public interface Sitemapper {

  Date getPreviousUpdate();

  void withEntryWriter(EntryWriter entryWriter);

}