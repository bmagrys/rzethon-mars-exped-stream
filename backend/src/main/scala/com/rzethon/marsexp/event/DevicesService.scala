package com.rzethon.marsexp.event

import java.time.LocalTime
import java.time.format.DateTimeFormatter

import akka.NotUsed
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import akka.util.Timeout
import com.rzethon.marsexp.{CorsSupport, JacksonWrapper}
import de.heikoseeberger.akkasse.EventStreamMarshalling._
import de.heikoseeberger.akkasse.ServerSentEvent

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._

class EventServiceApi(implicit system: ActorSystem, implicit val timeout: Timeout)
  extends EventService {

  def actorSystem = system

  override implicit def executionContext = system.dispatcher

  override implicit val requestTimeout = timeout

}

trait EventService
  extends EventActorApi
    with EventMarshalling
    with CorsSupport {

  import com.rzethon.marsexp.event.EventActor._

  def actorSystem: ActorSystem

  implicit val materializer = ActorMaterializer

  override def createEventActor: ActorRef =
    actorSystem.actorOf(EventActor.props, EventActor.name)

  val routes =
    addRoute ~
      corsHandler(getRoute)

  def addRoute =
    pathPrefix("event") {
      pathEndOrSingleSlash {
        post {
          entity(as[DeviceInfo]) { ev =>
            onSuccess(addEvent(ev)) {
              case EventDeviceInfoUpdated =>
                complete(HttpResponse(Created))
            }
          }
        }
      }

    }

  def getRoute =
    path("events") {
      get {
        complete {
          var deviceInfo: DeviceInfo
          var serialized: String

          val fut = getEvent
          Await.result(fut, 2 seconds)
          fut.onSuccess {
            case ev =>
              deviceInfo = ev
          }
          serialized = JacksonWrapper.serialize(deviceInfo)

          Source
            .tick(2.seconds, 2.seconds, NotUsed)
              .map(_ => {
                val fut = getEvent
                Await.result(fut, 1 seconds)
                fut.onSuccess {
                  case ev =>
                    deviceInfo = ev
                }
                serialized = JacksonWrapper.serialize(deviceInfo)
                }
              )
            .map(_ => ServerSentEvent(serialized))
            .keepAlive(1.second, () => ServerSentEvent.heartbeat)
        }
      }
    }

  def eventToServerSentEvent(event: Event) =
    ServerSentEvent(event.toString)

  def timeToServerSentEvent(time: LocalTime) =
    ServerSentEvent(DateTimeFormatter.ISO_LOCAL_TIME.format(time))

}

trait EventActorApi {

  import akka.pattern.ask
  import com.rzethon.marsexp.event.EventActor._

  implicit def executionContext: ExecutionContext

  implicit def requestTimeout: Timeout

  def createEventActor: ActorRef

  lazy val eventActor = createEventActor

  def getEvents =
    eventActor.ask(GetEvent).mapTo[Event]

  def addEvent(event: Event) =
    eventActor.ask(Add(event)).mapTo[EventResponse]

}