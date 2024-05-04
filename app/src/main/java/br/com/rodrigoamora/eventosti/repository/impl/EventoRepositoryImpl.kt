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

    override fun listarEventos(page: Int, size: Int): MediatorLiveData<Resource<List<Evento>?>> {
        this.mediator.addSource(this.listarEventosDoBanco()) { salonsFound ->
            mediator.value = Resource(salonsFound)
        }

        val failuresFromWebApiLiveData = MutableLiveData<Resource<List<Evento>?>>()
        this.mediator.addSource(failuresFromWebApiLiveData) { resourceOfFailure ->
            val currentResource = mediator.value
            val newResource: Resource<List<Evento>?> = if(currentResource != null){
                Resource(result = currentResource.result)
            } else {
                resourceOfFailure
            }
            mediator.value = newResource
        }

        this.listarEventos(page, size,
            failure = { errorCode ->
                failuresFromWebApiLiveData.value = Resource(result = null, error = errorCode)
            }
        )

        return this.mediator
    }

    private fun listarEventos(page: Int, size: Int,
                              failure: (errorCode: Int) -> Unit) {
        this.eventoWebClient.listarEventos(page, size,
            completion = { eventos ->
                salvarNoBanco(eventos)
            },
            failure = { errorCode ->
                errorCode?.let { failure(it) }
            }
        )
    }

    private fun listarEventosDoBanco(): LiveData<List<Evento>> {
        return eventoDao.listarTodos()
    }

    private fun salvarNoBanco(salonList: List<Evento>) {
        CoroutineScope(IO).launch {
            eventoDao.save(salonList)
        }
    }

}
