package scipr

import org.junit.Test

class ServerTest {
  
	@Test def test() {
	  println("hello world")
	  
	  val unit = new StaticServer(
	      "foo", 
	      "8080", 
	      "foo", 
	      "/Users/demian/code/demian0311.github.com/_site")
	  val result = unit.getFileContents("resume.html")
	  println("result: " + result)
	  println("arr: " + result.get.array())
	  
	}
  
}