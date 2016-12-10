package com.rzethon.marsexp.event

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.util.Timeout

import scala.concurrent.ExecutionContext

class EventServiceApi(implicit system: ActorSystem, implicit val timeout: Timeout)
  extends EventService {

  def actorSystem = system

  override implicit def executionContext = system.dispatcher

  override implicit val requestTimeout = timeout
}

trait EventService
  extends EventActorApi
    with EventMarshalling {

  import com.rzethon.marsexp.event.EventActor._

  def actorSystem: ActorSystem

  override def createEventActor: ActorRef =
    actorSystem.actorOf(EventActor.props, EventActor.name)

  val routes =
    addRoute

  def addRoute =
    pathPrefix("event") {
      pathEndOrSingleSlash {
        post {
          entity(as[Event]) { ev =>
            onSuccess(addEvent(ev)) {
              case EventAdded =>
                complete(HttpResponse(Created))
            }
          }
        }
      }

    }
}

trait EventActorApi {

  import akka.pattern.ask
  import com.rzethon.marsexp.event.EventActor._

  implicit def executionContext: ExecutionContext

  implicit def requestTimeout: Timeout

  def createEventActor: ActorRef

  lazy val eventActor = createEventActor

  def addEvent(event: Event) =
    eventActor.ask(Add(event)).mapTo[EventResponse]

}