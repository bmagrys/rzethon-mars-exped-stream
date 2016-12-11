package com.rzethon.marsexp.elastic

import java.time.{LocalDateTime, LocalTime, ZoneId}
import java.time.format.DateTimeFormatter

import akka.actor.Status.{Failure, Success}
import akka.actor.{Actor, ActorLogging, Props}
import com.rzethon.marsexp.event.DevicesActor.DeviceInfo
import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.{ElasticClient, ElasticsearchClientUri, Indexable}

object Elastic4s {

  def props = Props(new Elastic4s)

  def name = "elastic-actor"

  case class Add(deviceInfo: DeviceInfo)

  case class Search(name: String)

  case object WeatherEvent {
    def fromDeviceInfo(deviceInfo: DeviceInfo): WeatherEvent = {
      WeatherEvent(
        DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now(ZoneId.of("Europe/Warsaw"))),
        deviceInfo.name,
        deviceInfo.positionX,
        deviceInfo.positionY,
        deviceInfo.temperature,
        deviceInfo.humidity,
        deviceInfo.lightIntensity,
        deviceInfo.vibrations,
        deviceInfo.gasConcentration
      )
    }
  }

  case class WeatherEvent(
                           timestamp: String,
                           name: String,
                           positionX: Double,
                           positionY: Double,
                           temperature: Double,
                           humidity: Double,
                           lightIntensity: Double,
                           vibrations: Long,
                           gasConcentration: Double
                         )

  implicit object WeatherEventIndexable extends Indexable[WeatherEvent] {
    override def json(we: WeatherEvent): String =
      s""" { "timestamp" : "${we.timestamp}",
         |"name" : "${we.name}",
         |"positionX" : ${we.positionX},
         |"positionY" : ${we.positionY},
         |"temperature" : ${we.temperature},
         |"humidity" : ${we.humidity},
         |"lightIntensity" : ${we.lightIntensity},
         |"vibrations" : ${we.vibrations},
         |"gasConcentration" : ${we.gasConcentration} } """.stripMargin
  }

}

class Elastic4s extends Actor with ActorLogging {
  import com.rzethon.marsexp.elastic.Elastic4s._

  import context.dispatcher

  val client = ElasticClient.transport(ElasticsearchClientUri("elasticsearch://localhost:9300?cluster.name=rzethon_analytics"))

  override def receive: Receive = {
    case Add(deviceInfo) => {
      log.info("Putting to elasticsearch")
      val event = WeatherEvent.fromDeviceInfo(deviceInfo)
      client.execute {
        indexInto("weather" / "event").doc(event)
      }
    }
    // case Search(name) => {
    //   log.info("Searching in elasticsearch")
    //   val list = client.execute {
    //     search in "weather"->"event" query "Dev*"
    //   }.await
    //   log.info(list.toString)
      // list.onSuccess {
        // case list =>
        // sender() ! list
      // }
    // }
    case _: Any => log.error("Wrong message")
  }

}
