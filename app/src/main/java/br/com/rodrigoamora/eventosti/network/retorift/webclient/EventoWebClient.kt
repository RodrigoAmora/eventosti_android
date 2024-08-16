package br.com.rodrigoamora.eventosti.network.retorift.webclient

import br.com.rodrigoamora.eventosti.model.Evento
import br.com.rodrigoamora.eventosti.network.retorift.service.EventoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import java.util.Collections.list

class EventoWebClient(
    private val service: EventoService
) {

    private fun<T> executeRequest(
        call: Call<T>,
        completion: (result: T?) -> Unit,
        failure: (errorCode: Int) -> Unit
    ) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                when (val responseCode = response.code()) {
                    in 200..299 -> {
                        completion(response.body())
                    }
                    else -> {
                        failure(responseCode)
                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                failure(call.hashCode())
            }
        })
    }

    fun listarEventos(page: Int, completion: (eventos: List<Evento>) -> Unit,
                      failure: (errorCode: Int) -> Unit) {
        executeRequest(this.service.listarEventos(page),
            completion = { eventoResponse ->
                eventoResponse?.let { completion(it.eventos) }
            },
            failure = { errorCode ->  failure(errorCode) }
        )
    }

    fun buscarEventosPorNome(nome: String,
                             page: Int,
                             completion: (eventos: List<Evento>) -> Unit,
                             failure: (errorCode: Int) -> Unit) {
        executeRequest(this.service.buscarEventosPorNome(nome, page),
            completion = { eventoResponse ->
                eventoResponse?.let { completion(it.eventos) }
            },
            failure = { errorCode ->
                if (errorCode == 404) {
                    completion(ArrayList<Evento>())
                }
                failure(errorCode)
            }
        )
    }

}
