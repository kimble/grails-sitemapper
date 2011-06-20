package grails.plugins.sitemapper;

import java.io.IOException;
import java.util.Date;

public interface EntryWriter {

    void addEntry(String location, Date modifiedAt) throws IOException;

    void addEntry(String location, Date modifiedAt, ContentChangeFrequency freq, double priority) throws IOException;

}