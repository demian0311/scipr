package scipr

import scala.xml.Elem

class ConfigParser {
    
    def parse(xml: Elem): Option[Map[String, Server]] = {
        println(xml.label)
        
        assert(xml.label == "config")
        
        val servers = for {
            currServerXml <- xml \ "server"
        } yield(currServerXml)
        
        println("servers: "  + servers)
        
        
        return None
    }
    
    def createServerFromXml(xml: Elem): Option[Server] = {
        val port = xml \ "@port"
        println("port: " + port)
        
        val location = xml \ "location"
        val serverType = location \ "@type"
        val name = location \ "@name"
        //val path = location" \ "@path"
        
        
        None
    }
}