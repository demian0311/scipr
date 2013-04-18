package scipr

import org.junit.Test

class ConfigFinderTest {
    @Test def test(){
        println("hello world")
        val unit = new ConfigFinder()
        val result = unit.xmlFromFilename("src/test/resources/scipr-config.xml")
        println("result: " + result)
    }
}