package demo

import grails.plugins.sitemapper.EntryWriter;
import grails.plugins.sitemapper.Sitemapper;


class ForumSitemapper implements Sitemapper {
    
    String name = "forum"
	
	Date getPreviousUpdate() {
		new Date()
	}
	
	@Override
	public void withEntryWriter(EntryWriter entryWriter) {
		
	}
	
}