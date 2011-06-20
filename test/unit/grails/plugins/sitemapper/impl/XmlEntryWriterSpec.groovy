package grails.plugins.sitemapper.impl

import grails.plugin.spock.UnitSpec
import grails.plugins.sitemapper.SitemapperException
import spock.lang.Unroll

class XmlEntryWriterSpec extends UnitSpec {

    @Unroll("Writing tag #tagName with value #value")
    def "Should be able to format xml tags correctly"() {
        given:
        Appendable output = Mock()
        XmlEntryWriter entryWriter = new XmlEntryWriter(output, "http://developer-b.com");

        when:
        entryWriter.printTag(tagName, value)

        then:
        1 * output.append(expected)

        where:
        tagName    | value                                                             | expected
        "loc"      | 'http://www.example.com/catalog?item=12&desc=vacation_hawaii'     | "<loc>http://www.example.com/catalog?item=12&amp;desc=vacation_hawaii</loc>"
        "escaped"  | '''&'"><'''                                                       | "<escaped>&amp;&apos;&quot;&gt;&lt;</escaped>"
    }

    def "Priority should be between zero and one"() {
        given:
        Appendable output = Mock()
        XmlEntryWriter entryWriter = new XmlEntryWriter(output, null);
        
        when: entryWriter.printPriority(-0.1)
        then: SitemapperException ex = thrown()
        
        when: entryWriter.printPriority(1.1)
        then: ex = thrown()
        
        when: entryWriter.printPriority(0.1)
        then: 1 * output.append("<priority>0.1</priority>")
    }
}