package br.com.rodrigoamora.eventosti.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import br.com.rodrigoamora.eventosti.database.dao.EventoDao
import br.com.rodrigoamora.eventosti.model.Evento
import br.com.rodrigoamora.eventosti.network.retorift.webclient.EventoWebClient
import br.com.rodrigoamora.eventosti.repository.EventoRepository
import br.com.rodrigoamora.eventosti.repository.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class EventoRepositoryImpl(
    private val eventoDao: EventoDao,
    private val eventoWebClient: EventoWebClient
): EventoRepository {

    private val mediator = MediatorLiveData<Resource<List<Evento>?>>()

    override fun buscarEventosDoBancoDeDadosNaAPI(page: Int): MediatorLiveData<Resource<List<Evento>?>> {
        this.mediator.addSource(this.buscarEventosDoBancoDeDados()) { eventos ->
            mediator.value = Resource(eventos)
        }

        val failuresFromWebApiLiveData = MutableLiveData<Resource<List<Evento>?>>()
        this.mediator.addSource(failuresFromWebApiLiveData) { resourceOfFailure ->
            val currentResource = this.mediator.value
            val newResource: Resource<List<Evento>?> = if (currentResource != null) {
                Resource(result = currentResource.result)
            } else {
                resourceOfFailure
            }
            this.mediator.value = newResource
        }

        this.listarEventos(page,
            failure = { errorCode ->
                val currentResource = mediator.value
                failuresFromWebApiLiveData.value = Resource(result = currentResource?.result, error = errorCode)
            }
        )

        return this.mediator
    }

    private fun listarEventos(page: Int,
                              failure: (errorCode: Int) -> Unit) {
        this.eventoWebClient.listarEventos(page,
            completion = { eventos ->
                eventos?.let {
                    salvarNoBanco(it)
                }
            },
            failure = { errorCode ->
                errorCode?.let {
                    failure(it)
                }
            }
        )
    }

    override fun buscarEventosDoBancoDeDados(): LiveData<List<Evento>> = this.eventoDao.listarTodos()

    private fun salvarNoBanco(eventos: List<Evento>) {
        CoroutineScope(IO).launch {
            eventoDao.save(eventos)
        }
    }

    private fun apagarTodosOsEventos() {
        CoroutineScope(IO).launch {
            eventoDao.apagarTodos()
        }
    }

}
