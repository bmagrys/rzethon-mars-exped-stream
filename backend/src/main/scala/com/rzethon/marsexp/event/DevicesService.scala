package com.rzethon.marsexp.event

import akka.NotUsed
import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import akka.util.Timeout
import com.rzethon.marsexp.{CorsSupport, JacksonWrapper}
import de.heikoseeberger.akkasse.EventStreamMarshalling._
import de.heikoseeberger.akkasse.ServerSentEvent

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}

class DevicesServiceApi(implicit system: ActorSystem, implicit val timeout: Timeout)
  extends DevicesService {

  def actorSystem: ActorSystem = system

  override implicit def executionContext: ExecutionContext = system.dispatcher

  override implicit val requestTimeout: Timeout = timeout

}

trait DevicesService
  extends DevicesActorApi
    with EventMarshalling
    with CorsSupport {

  import com.rzethon.marsexp.event.DevicesActor._

  def actorSystem: ActorSystem

  val log = Logging(actorSystem.eventStream, "rzethon")

  implicit val materializer = ActorMaterializer

  override def createDevicesActor: ActorRef =
    actorSystem.actorOf(DevicesActor.props, DevicesActor.name)

  val routes: Route =
    updateDeviceInfoRoute ~
      corsHandler(getDeviceByNameRoute) ~
      corsHandler(getDevicesRoute)

  def updateDeviceInfoRoute(): Route =
    pathPrefix("event") {
      pathEndOrSingleSlash {
        post {
          entity(as[DeviceInfo]) { deviceInfo =>
            onSuccess(updateDeviceInfo(deviceInfo)) {
              case EventDeviceInfoUpdated =>
                complete(HttpResponse(Created))
            }
          }
        }
      }

    }

  def getDeviceByNameRoute: Route =
    path("device") {
      get {
        parameters('name.as[String]) { name =>
          complete {
            var deviceInfo = DeviceInfo("", 0, 0, 0, 0, 0, 0, 0)
            Source
              .tick(2.seconds, 2.seconds, NotUsed)
              .map(_ => {
                log.info(s"Getting device info by name: '$name'")
                val fut = getDeviceInfo(name)
                Await.result(fut, 1.seconds)
                fut.onSuccess {
                  case di => di.map(di =>
                    deviceInfo = di
                  )
                }
                JacksonWrapper.serialize(deviceInfo)
              })
              .map(serialized => ServerSentEvent(serialized))
          }
        }
      }
    }

  def getDevicesRoute: Route =
    pathPrefix("devices") {
      get {
        onSuccess(getDevices) { devices =>
          complete(OK, devices)
        }
      }
    }

  def eventToServerSentEvent(deviceInfo: DeviceInfo) =
    ServerSentEvent(deviceInfo.toString)

}

trait DevicesActorApi {

  import akka.pattern.ask
  import com.rzethon.marsexp.event.DevicesActor._

  implicit def executionContext: ExecutionContext

  implicit def requestTimeout: Timeout

  def createDevicesActor: ActorRef

  lazy val devicesActor: ActorRef = createDevicesActor

  def getDeviceInfo(name: String): Future[Option[DeviceInfo]] =
    devicesActor.ask(GetDeviceInfo(name)).mapTo[Option[DeviceInfo]]

  def getDevices: Future[List[String]] =
    devicesActor.ask(GetDevices).mapTo[List[String]]

  def updateDeviceInfo(deviceInfo: DeviceInfo): Future[EventResponse] =
    devicesActor.ask(Update(deviceInfo)).mapTo[EventResponse]

}