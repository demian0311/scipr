package scipr

import org.junit.Test

class ConfigParserTest {
    
    // val someXML = XML.loadString(someXMLInAString)

    
    @Test def test(){
        val unit = new ConfigParser()
        val result = unit.parse(<config>
            <server port="8099">
                <location 
                    name="static resources"
                    path="/static" 
                    type="static" 
                    root="/Users/demian/code/demian0311.github.com/_site"/>
            </server>
            </config>)
        
        println("result: " + result)
    }
    
    @Test def createServerFromXml(){
        val unit = new ConfigParser()
        val result = unit.createServerFromXml(<server port="8099">
                <location 
                    type="static" 
                    name="static resources" 
                    path="/static" 
                    root="/Users/demian/code/demian0311.github.com/_site"></location>
            </server>)
        
        println("result: " + result)
    }

}