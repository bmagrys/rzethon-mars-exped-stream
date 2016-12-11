package com.rzethon.marsexp.event

import akka.actor.{Actor, ActorLogging, Props}

object DevicesActor {
  def props = Props(new DevicesActor)

  def name = "event-actor"

  case object GetDevices

  case class DeviceInfo(
                     name: String,
                     positionX: Double,
                     positionY: Double,
                     temperature: Double,
                     humidity: Double,
                     lightIntensity: Double,
                     vibrations: Long,
                     gasConcentration: Double
                   )

  case class GetDeviceInfo(name: String)

  case class Update(deviceInfo: DeviceInfo)

  sealed trait EventResponse

  case object EventDeviceInfoUpdated extends EventResponse

}

class DevicesActor extends Actor with ActorLogging {

  import DevicesActor._

  var devices: Map[String, DeviceInfo] = Map.empty

  override def receive: Receive = {
    case Update(deviceInfo) =>
      log.info("Updating device info: " + deviceInfo)
      this.devices + (deviceInfo.name -> deviceInfo)
      devices = devices + (deviceInfo.name -> deviceInfo)
      sender() ! EventDeviceInfoUpdated
    case GetDeviceInfo(name) =>
      log.info("Get device info for name: " + name)
      sender() ! devices.get(name)
    case GetDevices =>
      val names = devices.values.map(info => info.name).toList
      log.info("Devices: " + names)
      sender() ! names
  }

}
