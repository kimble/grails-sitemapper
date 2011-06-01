package demo

import grails.plugins.sitemapper.EntryWriter;
import grails.plugins.sitemapper.Sitemapper;


class ForumSitemapper implements Sitemapper {
    
    String name = "forum"
    
    public ForumSitemapper() {
        println "Constructed forum sitemapper";
    }
	
	Date getPreviousUpdate() {
		new Date() 
	}
	
	@Override
	public void withEntryWriter(EntryWriter entryWriter) {
		// ...
	}
	
}