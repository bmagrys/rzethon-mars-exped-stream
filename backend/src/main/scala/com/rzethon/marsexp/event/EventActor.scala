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

  case object GetEvent

  case class Add(event: Event)

  sealed trait EventResponse

  case object EventAdded extends EventResponse

}

class EventActor extends Actor with ActorLogging {

  import EventActor._

  var event: Event = Event("Initial event", 1, 2, 3, 4, 5, 6, 7)

  override def receive: Receive = {
    case Add(event) =>
      log.info("Setting event: " + event)
      this.event = event
      sender() ! EventAdded
    case GetEvent =>
      log.info("Getting event: " + event)
      sender() ! event
  }

}
