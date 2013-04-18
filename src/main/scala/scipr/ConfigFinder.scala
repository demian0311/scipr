package scipr

import scala.xml.Elem
import scala.io._
import scala.xml.XML

object ConfigFinder{
    def apply() { 
        new ConfigFinder()
    }
}


/**
 * This takes in the name of a config file and tries to 
 * find it either on the file system or on the classpath.
 * 
 * It returns XML from the contents of the config file.
 */
class ConfigFinder {

    def xmlFromFilename(filename: String): Option[Elem] = {
        try {
            val result = XML.load(new java.io.InputStreamReader(new java.io.FileInputStream(filename), "UTF-8"))
            Some(result)
        } catch {
            case _ => None
        }
    }
}