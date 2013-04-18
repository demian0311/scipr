package scipr

import org.junit.Test

class ConfigParserTest {
    
    // val someXML = XML.loadString(someXMLInAString)

    
    @Test def test(){
        println("hello world")
        
        val xml = 
            <config>
            <server port="8099">
                <location 
                    name="static resources"
                    path="/static" 
                    type="static" 
                    root="/Users/demian/code/demian0311.github.com/_site"/>
            </server>
            </config>
        
        val unit = new ConfigParser()
        val result = unit.parse(xml)
        
        println("result: " + result)
    }
    
    @Test def createServerFromXml(){
        val xml = 
            <server port="8099">
                <location type="static" name="static resources" path="/static" root="/Users/demian/code/demian0311.github.com/_site"></location>
            </server>
            
        val unit = new ConfigParser()
        val result = unit.createServerFromXml(xml)
        
        println("result: " + result)
    }

}