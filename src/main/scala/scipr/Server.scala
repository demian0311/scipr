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

/**
 * This works at a very basic level but much to do.
 *
 * I think the servers know how to parse their own XML.
 * Possibly this is an alternate constructor or apply
 * method in an object.
 */
class StaticServer(
    val name: String,
    val port: String,
    val path: String,
    val root: String) extends Server {

    def start() {
        val service = new Service[HttpRequest, HttpResponse] {
            def apply(req: HttpRequest): Future[HttpResponse] = {
                val timer = new Timer(req.getUri())

                getFileContentsAsChannelBuffer(req.getUri()) match {
                    case Right(channelBuffer) => {
                        val response = new DefaultHttpResponse(
                            req.getProtocolVersion, HttpResponseStatus.OK)
                        response.setContent(channelBuffer)
                        if (req.getUri().endsWith("png")) {
                            response.setHeader("Content-Type", "image/png")
                            response.setHeader("Content-Length", channelBuffer.array().length)
                        }
                        response.setHeader("Server", "scipr/0.1")
                        println(timer.stopString())
                        Future.value(response)
                    }
                    case Left(failureResponse) => {
                        val response = new DefaultHttpResponse(
                            req.getProtocolVersion, failureResponse.status)
                        println(timer.stopString())
                        response.setHeader("Server", "scipr/0.1")
                        Future.value(response)
                    }
                }

            }
        }

        val server = Http.serve(":" + port, service)
        Await.ready(server)
    }

    val binaryFileExtensions = List("jpg", "jpeg", "png", "gif", "ico")
    val indexFilename = "/index.html"
        
    def isFileBinary(filename: String): Boolean = {
        binaryFileExtensions.find(filename.toLowerCase.endsWith(_)) match {
            case Some(theExtension) => true
            case _ => false
        }
    }

    def getFileContentsAsChannelBuffer(filename: String): Either[FailureResponse, ChannelBuffer] = {
        import java.io.File
        import java.io.FileInputStream
        
        val modifiedFilename = if (filename == "/") indexFilename else filename

        try {
            if (isFileBinary(modifiedFilename)) {
                // process as binary file
                val file = new File(root + modifiedFilename)
                val in = new FileInputStream(file)
                val bytes = new Array[Byte](file.length.toInt)
                in.read(bytes)
                in.close()

                Right(ChannelBuffers.copiedBuffer(bytes))
            } else {
                // process as text file
                val bufferedSource = scala.io.Source.fromFile(root + modifiedFilename)
                val charArray = bufferedSource.toArray
                val channelBuffer = ChannelBuffers.copiedBuffer(charArray, Charset.forName("UTF-8"))

                Right(channelBuffer)
            }
        } catch {
            case fnfe: FileNotFoundException => {
                fnfe.printStackTrace()
                Left(FailureResponse(NotFound, "could not find file: " + modifiedFilename))
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