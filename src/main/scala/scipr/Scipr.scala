package scipr

object Scipr extends App {
    val configFinder = new ConfigFinder()
    println("args: " + args)
    
    val configFilename = args(0)
    println("configFilename: " + configFilename)
    
    val xml = configFinder.xmlFromFilename(configFilename)
    
    val configParser = new ConfigParser()
    val result = configParser.parse(xml.get) // TODO-DLN: badness
    
    println("starting servers")
    result.get.first.start
    
}