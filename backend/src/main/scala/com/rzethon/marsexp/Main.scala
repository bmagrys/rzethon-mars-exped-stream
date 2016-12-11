package com.rzethon.marsexp

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import com.rzethon.marsexp.event.DevicesActor._
import com.rzethon.marsexp.event.DevicesServiceApi

import scala.io.StdIn

object Main extends App with Startup with RequestTimeout {

  val config = ConfigFactory.load()

  implicit val host = config.getString("http.host")
  implicit val port = config.getInt("http.port")
  implicit val system = ActorSystem("invitationSystem")
  implicit val timeout = configuredRequestTimeout(config)

  val api = new RestApi {
    override def invitationServiceApi: DevicesServiceApi = new DevicesServiceApi()
  }

  startHttpServer(api.routes, host, port)

}
