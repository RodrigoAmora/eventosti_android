package br.com.rodrigoamora.eventosti.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.rodrigoamora.eventosti.database.dao.EventoDao
import br.com.rodrigoamora.eventosti.model.Evento


@Database(entities = [Evento::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase: RoomDatabase() {
    abstract fun eventoDao(): EventoDao
}