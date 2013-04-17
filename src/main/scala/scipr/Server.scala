package scipr

trait Server {
    def start()
    def stop()
}

class StaticServer extends Server {
    def start(){
        // http://twitter.github.io/finagle/guide/Quickstart.html
//          val service = new Service[HttpRequest, HttpResponse] {
//    def apply(req: HttpRequest): Future[HttpResponse] =
//      Future.value(new DefaultHttpResponse(
//        req.getProtocolVersion, HttpResponseStatus.OK))
//  }
//  val server = Http.serve(":8080", service)
//  Await.ready(server)
    }
    
    def stop(){
        
    }
}