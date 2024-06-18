package br.com.rodrigoamora.eventosti.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.rodrigoamora.eventosti.model.Evento
import br.com.rodrigoamora.eventosti.repository.EventoRepository
import br.com.rodrigoamora.eventosti.repository.Resource

class EventoViewModel(
    private val eventoRepository: EventoRepository
): ViewModel() {

    fun buscarEventos(page: Int): LiveData<Resource<List<Evento>?>> {
        return this.eventoRepository.buscarEventosDoBancoDeDadosNaAPI(page)
    }

    fun buscarEventosDoBancoDeDados(): LiveData<List<Evento>> {
        return this.eventoRepository.buscarEventosDoBancoDeDados()
    }
}
