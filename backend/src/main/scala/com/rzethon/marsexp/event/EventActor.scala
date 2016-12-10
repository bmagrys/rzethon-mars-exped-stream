package com.rzethon.marsexp.event

import akka.actor.{Actor, ActorLogging, Props}

object EventActor {
  def props = Props(new EventActor)

  def name = "event-actor"

  case class Event(
                    name: String,
                    positionX: Double,
                    positionY: Double,
                    temperature: Double,
                    humidity: Double,
                    lightIntensity: Double,
                    vibrations: Long,
                    gasConcentration: Double
                  )

  case class Add(event: Event)

  sealed trait EventResponse

  case object EventAdded extends EventResponse

}

class EventActor extends Actor with ActorLogging {

  import EventActor._

  override def receive: Receive = {
    case Add(event) =>
      log.debug("Adding event: " + event)
      sender() ! EventAdded
  }

}
