package br.com.rodrigoamora.eventosti.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import br.com.rodrigoamora.eventosti.model.Evento

interface EventoRepository {
    fun buscarEventosDoBancoDeDadosNaAPI(page: Int): MediatorLiveData<Resource<List<Evento>?>>
    fun buscarEventosPorNomeNaAPI(nome: String, page: Int): MediatorLiveData<Resource<List<Evento>?>>
    fun buscarEventosDoBancoDeDados(): LiveData<List<Evento>>
}
