package br.com.rodrigoamora.eventosti.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Evento (

    @PrimaryKey
    @SerializedName("id")
    var id: Long,

    @SerializedName("nome")
    var nome: String,

    @SerializedName("descricao")
    var descricao: String?,

    @SerializedName("site")
    var site: String,

    @SerializedName("dataInicio")
    var dataInicio: String,

    @SerializedName("dataFim")
    var dataFim: String,

    @SerializedName("tipoEvento")
    var tipoEvento: TipoEvento

): Serializable
{
    fun formatarData(): String {
        if (dataInicio == dataFim) {
            return dataInicio
        }
        return "$dataInicio - $dataFim"
    }
}
