package br.com.rodrigoamora.eventosti.network.reponse

import br.com.rodrigoamora.eventosti.model.Evento
import com.google.gson.annotations.SerializedName

data class EventoResponse (
    @SerializedName("content")
    var eventos: List<Evento>,
)
