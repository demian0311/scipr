package scipr

import com.twitter.finagle.{ Http, Service }
import com.twitter.util.{ Await, Future }
import java.net.InetSocketAddress
import org.jboss.netty.handler.codec.http._
import org.jboss.netty.buffer.ChannelBuffer
import org.jboss.netty.buffer.ChannelBufferInputStream
import java.io.BufferedReader
import org.jboss.netty.buffer.ChannelBuffers
import java.nio.charset.Charset
import java.io.FileNotFoundException
import com.twitter.finagle.http.Status._

import com.twitter.finagle.http.Status
import org.jboss.netty.handler.codec.http.HttpResponseStatus

case class FailureResponse(status: HttpResponseStatus, message: String)

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
        val service = new Service[HttpRequest, HttpResponse] {
            def apply(req: HttpRequest): Future[HttpResponse] = {
                //val channelBuffer = getFileContentsAsChannelBuffer(filename)
                println("uri: " + req.getUri())
               
//                val result = getFileContentsAsChannelBuffer(req.getUri())
//                result match {
//                    
//                }
                
                val response = new DefaultHttpResponse(
                    req.getProtocolVersion, HttpResponseStatus.OK)
                //response.setContent()
                //response.setContent(content)

                Future.value(response)
                // this is where we look at the file system and send 
                // what we find from there
            }
        } 
        val server = Http.serve(":" + port, service)
        Await.ready(server)

    }

    def getFileContentsAsChannelBuffer(filename: String): Either[FailureResponse, ChannelBuffer] = {
        try {
            val bufferedSource = scala.io.Source.fromFile(root + "/" + filename)
            val charArray = bufferedSource.toArray
            val channelBuffer = ChannelBuffers.copiedBuffer(charArray, Charset.forName("UTF-8"))
            Right(channelBuffer)
        } catch {
            case fnfe: FileNotFoundException => {
                Left(FailureResponse(NotFound, "could not find file: " + filename))
            }
            case e => {
                Left(FailureResponse(InternalServerError, e.getMessage()))
            }
        }
    }

    def stop() {
        println("stopping")
    }
}