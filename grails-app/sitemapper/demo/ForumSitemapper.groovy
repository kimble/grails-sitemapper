package demo

import static grails.plugins.sitemapper.ContentChangeFrequency.*
import grails.plugins.sitemapper.EntryWriter
import grails.plugins.sitemapper.Sitemapper


class ForumSitemapper implements Sitemapper {
    
    Date previousUpdate = new Date()
    	
	@Override
	public void withEntryWriter(EntryWriter entryWriter) {
        entryWriter.addEntry "/forum/entry/test", new Date() - 1
        entryWriter.addEntry "/forum/entry/test-2", new Date(), MONTHLY, 0.5
	}
	
}