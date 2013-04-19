package scipr

import scala.xml.Elem
import scala.xml.Node

object ConfigParser {
    def apply(){
        new ConfigParser()
    }
}

class ConfigParser {
    
    def parse(xml: Elem): Option[Seq[Server]] = {
        println(xml.label)
        
        assert(xml.label == "config")
        
        val servers = for {
            currServerXml <- xml \ "server"
            currServerInstance <- createServerFromXml(currServerXml)
        } yield(currServerInstance)
        
        Some(servers)
    }
    
    def createServerFromXml(xml: Node): Option[Server] = {
        val port = xml \ "@port"
        println("port: " + port)
        
        val location = xml \ "location"
        val serverType = location \ "@type"
        val name = location \ "@name"
        val path = location \ "@path"
        val root = location \ "@root"

        serverType.text match {
            case "static" => {
                val newStaticServer = new StaticServer(
                        name=name.text, 
                        port=port.text,
                        path=path.text, 
                        root=root.text)
                Some(newStaticServer)
            }
            case unmatchedServerType => {
                None
            }
        }
    }
}