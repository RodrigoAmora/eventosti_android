package br.com.rodrigoamora.eventosti.repository

import androidx.lifecycle.MediatorLiveData
import br.com.rodrigoamora.eventosti.model.Evento

interface EventoRepository {
    fun listarEventos(page: Int, size: Int): MediatorLiveData<Resource<List<Evento>?>>
}
