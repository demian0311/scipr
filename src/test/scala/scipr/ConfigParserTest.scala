package scipr

import org.junit.Test

class ConfigParserTest {
    
    @Test def test(){
        println("hello world")
        
        val xml = 
            <config>
            <server port="8099">
                <location path="/static">
                 <staticFileServer>
                     <root>/Users/demian/code/demian0311.github.com/_site</root>
                 </staticFileServer>
                </location>
            </server>
            </config>
        
        val unit = new ConfigParser()
        val result = unit.parse(xml)
        
        println("result: " + result)
        
    }

}