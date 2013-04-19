package scipr

import org.junit.Test
import java.nio.charset.Charset
import org.junit.Assert._
import com.twitter.finagle.http.Status._

class ServerTest {

    val unit = new StaticServer("", "1", "",
        "src/test/resources")

    @Test def getFileContentsAsChannelBuffer() {
        unit.getFileContentsAsChannelBuffer("index.html") match {
            case Right(value) => {
                val asString = value.toString(Charset.forName("UTF-8")).stripLineEnd
                assertEquals("<html><h1>ohai</h2></html>", asString)
            }
            case Left(failureResponse) => fail(failureResponse.message)
        }
    }

    @Test def getFileContentsAsChannelBuffer_notFound() {
        unit.getFileContentsAsChannelBuffer("missing.html") match {
            case Left(FailureResponse(NotFound, message)) => {
                assertEquals("could not find file: missing.html", message)
            }
            case _                                        => fail("should have returned a None")
        }
    }
}