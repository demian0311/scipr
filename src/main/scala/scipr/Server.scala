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
                val timer = new Timer(req.getUri())

                getFileContentsAsChannelBuffer(req.getUri()) match {
                    case Right(channelBuffer) => {
                        val response = new DefaultHttpResponse(
                            req.getProtocolVersion, HttpResponseStatus.OK)
                        response.setContent(channelBuffer)
                        println(timer.stopString())
                        Future.value(response)
                    }
                    case Left(failureResponse) => {
                        val response = new DefaultHttpResponse(
                            req.getProtocolVersion, failureResponse.status)
                        println(timer.stopString())
                        Future.value(response)
                    }
                }

            }
        }

        val server = Http.serve(":" + port, service)
        Await.ready(server)
    }

    def getFileContentsAsChannelBuffer(filename: String): Either[FailureResponse, ChannelBuffer] = {
        try {
            val bufferedSource = scala.io.Source.fromFile(root + filename)
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