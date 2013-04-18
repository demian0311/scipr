package scipr

import com.twitter.finagle.{ Http, Service }
import com.twitter.util.{ Await, Future }
import java.net.InetSocketAddress
import org.jboss.netty.handler.codec.http._

trait Server {
    def start()
    def stop()
}

class StaticServer(
    val name: String,
    val port: String,
    val path: String,
    val root: String) extends Server {
    def start() {
        println("starting")

        val service = new Service[HttpRequest, HttpResponse] {
            def apply(req: HttpRequest): Future[HttpResponse] =
                Future.value(new DefaultHttpResponse(
                    req.getProtocolVersion, HttpResponseStatus.OK))
            // this is where we look at the file system and send 
            // what we find from there
        }
        val server = Http.serve(":" + port, service)
        Await.ready(server)

    }

    def getFileContents(filename: String): Option[String] = {
        val source = scala.io.Source.fromFile("file.txt")
        val lines = source.mkString
        //source.close ()

        Some(lines)

    }

    def stop() {
        println("stopping")
    }
}