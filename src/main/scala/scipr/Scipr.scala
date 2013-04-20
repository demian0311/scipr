package scipr

/**
 * We need a higher level concept here.  Right now I have a server
 * bound to a port.  This probably isn't correct.  With the way
 * I have it now you can't have multiple servers running under the
 * same port.  For example you couldn't have /static serving up
 * static resources and then /app proxying calls to another server
 * all of that behind the same port.
 */
object Scipr extends App {
    val configFilename = args(0)
    val configFinder = new ConfigFinder()
    
    configFinder.xmlFromFilename(configFilename) match {
        case Some(xml) => {
            val configParser = new ConfigParser()
            configParser.parse(xml) match {
                case Some(servers) => {
                    servers.foreach{_.start}
                }
                case None => {
                    println("there were no servers to start in configuration file: " + configFilename)
                }
            }
        }
        case None => {
            println("unable to parse configuration file: " + configFilename)
        }
    }
}