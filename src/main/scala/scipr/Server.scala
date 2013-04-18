package scipr

trait Server {
    def start()
    def stop()
}

class StaticServer(
        val name: String, 
        val path: String, 
        val root: String) extends Server {
    def start(){
        println("starting")
    }
    
    def stop(){
        println("stopping")
    }
}