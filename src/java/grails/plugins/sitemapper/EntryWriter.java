package grails.plugins.sitemapper;

import java.io.IOException;
import java.util.Map;

interface EntryWriter {

	public abstract void addEntry(final Map<String, String> args)
			throws IOException;

}