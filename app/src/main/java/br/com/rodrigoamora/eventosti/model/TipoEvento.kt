package br.com.rodrigoamora.eventosti.model

import com.google.gson.annotations.SerializedName

enum class TipoEvento {
    @SerializedName("PRESENCIAL")
    PRESENCIAL,

    @SerializedName("ON_LINE")
    ON_LINE,

    @SerializedName("HIBIRDO")
    HIBIRDO
}
