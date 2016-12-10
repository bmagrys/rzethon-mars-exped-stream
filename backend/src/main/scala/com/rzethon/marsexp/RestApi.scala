package com.rzethon.marsexp

import com.rzethon.marsexp.event.EventServiceApi

trait RestApi {

  def invitationServiceApi: EventServiceApi

  def routes =
    invitationServiceApi.routes

}
