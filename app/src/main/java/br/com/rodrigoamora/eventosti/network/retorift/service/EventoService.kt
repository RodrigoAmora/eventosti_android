package br.com.rodrigoamora.eventosti.network.retorift.service

import br.com.rodrigoamora.eventosti.network.reponse.EventoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EventoService {
    @GET("api/evento")
    fun listarEventos(@Query("page") page: Int): Call<EventoResponse>

}
