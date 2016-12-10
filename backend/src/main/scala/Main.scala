import java.time.LocalTime
import java.time.format.DateTimeFormatter

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._
import scala.io.StdIn

object Main extends App {

  implicit val system = ActorSystem("rzethon-actors")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  val config = ConfigFactory.load()
  implicit val host = config.getString("http.host")
  implicit val port = config.getInt("http.port")

  val routes =
    path("events") {
      get {
        complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Rzethon!"))
      }
    }

  val bindingFuture = Http().bindAndHandle(routes, host, port)

  println(s"Server online at http://$host:$port. Press RETURN to stop...")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())

}
