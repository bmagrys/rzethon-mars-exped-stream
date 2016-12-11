package com.rzethon.marsexp

import com.rzethon.marsexp.event.DevicesServiceApi

trait RestApi {

  def invitationServiceApi: DevicesServiceApi

  def routes =
    invitationServiceApi.routes

}
