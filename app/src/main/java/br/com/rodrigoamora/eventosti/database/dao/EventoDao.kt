package br.com.rodrigoamora.eventosti.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import br.com.rodrigoamora.eventosti.model.Evento

@Dao
interface EventoDao {

    @Query("SELECT * FROM Evento")
    fun listarTodos(): LiveData<List<Evento>>

    @Insert(onConflict = REPLACE)
    fun save(evento: Evento)

    @Insert(onConflict = REPLACE)
    fun save(eventos: List<Evento>)

    @Delete
    fun apagar(evento: Evento)

    @Query("DELETE FROM Evento")
    fun apagarTodos()

}
