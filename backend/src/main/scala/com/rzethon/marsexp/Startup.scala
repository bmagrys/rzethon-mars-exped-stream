package com.rzethon.marsexp

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

import scala.concurrent.Future
import scala.io.StdIn

trait Startup {

  def startHttpServer(api: Route, host: String, port: Int)(implicit system: ActorSystem) = {
    implicit val executionContext = system.dispatcher
    implicit val materializer = ActorMaterializer()

    val bindingFuture: Future[ServerBinding] = Http().bindAndHandle(api, host, port)
    val log = Logging(system.eventStream, "rzethon")
    bindingFuture.map { serverBinding =>
      log.info(s"REST API bound to ${serverBinding.localAddress}. Please enter RETURN to stop...")
    }.onFailure {
      case ex: Exception =>
        log.error(ex, "Failed to bind to {}:{}!", host, port)
        system.terminate()
    }
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }

}
