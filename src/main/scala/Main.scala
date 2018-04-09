import java.io.{File, FileOutputStream, IOException}

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.{complete, path}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import scala.io.StdIn


object Main extends App {

  val config = ConfigFactory.load()

  val host   = config.getString("host")
  val port   = config.getInt("port")
  val fileName = new File(config.getString("filename"))

  implicit val system = ActorSystem("root")
  implicit val materializer = ActorMaterializer()
  implicit val executor = system.dispatcher

  val route : Route = {
    path("store") {
      post {
        entity(as[Array[Byte]]) { message ⇒
          try {
            val fop = new FileOutputStream(fileName, true)
            fop.write(message)
            fop.write("\n".getBytes)
            fop.flush()
            fop.close()
          } catch {
            case e: IOException ⇒ {
              println(s"IO Exception: $e")
            }
          }
          complete("")
        }
      }
    }
  }

  //start, listen to requests, shutdown
  val bindingFuture = Http().bindAndHandle(route, host, port)
  println(s"Send post message to http://$host:$port/store...")
  println(s"Push enter for stop service")
  StdIn.readLine()

  bindingFuture.flatMap(_.unbind()).onComplete(_ ⇒ system.terminate())

}