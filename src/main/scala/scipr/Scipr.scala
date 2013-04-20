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
    ConfigFinder().xmlFromFilename(configFilename) match {
        case Some(xml) => {
            ConfigParser().parse(xml) match {
                case Some(servers) => servers.foreach { _.start }
                case None => println("no servers in config file: " + configFilename)
            }
        }
        case None => {
            println("unable to parse config file: " + configFilename)
        }
    }
}