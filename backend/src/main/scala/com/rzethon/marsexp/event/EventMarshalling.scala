package com.rzethon.marsexp.event

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

trait EventMarshalling extends SprayJsonSupport with DefaultJsonProtocol {

  import com.rzethon.marsexp.event.DevicesActor._

  implicit val deviceInfoFormat = jsonFormat8(DeviceInfo)
}
