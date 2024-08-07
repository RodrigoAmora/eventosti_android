package br.com.rodrigoamora.eventosti.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE 'Evento' ADD COLUMN 'tipoEvento' TEXT NOT NULL")
    }
}
