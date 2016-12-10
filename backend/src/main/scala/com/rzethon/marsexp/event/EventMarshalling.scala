package com.rzethon.marsexp.event

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

trait EventMarshalling extends SprayJsonSupport with DefaultJsonProtocol {

  import com.rzethon.marsexp.event.EventActor._

  implicit val eventFormat = jsonFormat8(Event)
}
