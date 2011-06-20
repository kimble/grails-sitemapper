package grails.plugins.sitemapper.impl

import grails.plugin.spock.UnitSpec
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
        1 * output.append(expected);
        
        where:
        tagName    | value                                                             | expected
        "loc"      | 'http://www.example.com/catalog?item=12&desc=vacation_hawaii'     | "<loc>http://www.example.com/catalog?item=12&amp;desc=vacation_hawaii</loc>"
        "escaped"  | '''&'"><'''                                                       | "<escaped>&amp;&apos;&quot;&gt;&lt;</escaped>"
    }
    
}