package br.com.rodrigoamora.eventosti.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.rodrigoamora.eventosti.database.converter.Converters
import br.com.rodrigoamora.eventosti.database.dao.EventoDao
import br.com.rodrigoamora.eventosti.model.Evento

//@TypeConverters(Converters::class)
@Database(entities = [Evento::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase: RoomDatabase() {
    abstract fun eventoDao(): EventoDao
}