package br.com.rodrigoamora.eventosti.di

import androidx.room.Room
import br.com.rodrigoamora.eventosti.BuildConfig
import br.com.rodrigoamora.eventosti.database.AppRoomDatabase
import br.com.rodrigoamora.eventosti.database.migration.MIGRATION_1_2
import br.com.rodrigoamora.eventosti.network.retorift.AppRetrofit
import br.com.rodrigoamora.eventosti.network.retorift.webclient.EventoWebClient
import br.com.rodrigoamora.eventosti.repository.EventoRepository
import br.com.rodrigoamora.eventosti.repository.impl.EventoRepositoryImpl
import br.com.rodrigoamora.eventosti.ui.viewmodel.EventoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.dsl.module

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            databaseModule,
            repositoryModule,
            retrofitModule,
            servicesModule,
            viewModelModule,
            webClientModule
        )
    )
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppRoomDatabase::class.java,
            BuildConfig.DATABASE_NAME)
            .addMigrations(MIGRATION_1_2)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppRoomDatabase>().eventoDao() }
}

val repositoryModule = module {
    single<EventoRepository> { EventoRepositoryImpl(get(), get()) }
}

val retrofitModule = module {
    single { AppRetrofit(BuildConfig.BASE_URL_API).instantiateRetrofit() }
}

val servicesModule = module {
    single { AppRetrofit(BuildConfig.BASE_URL_API).eventoService() }
}

val viewModelModule = module {
    viewModel { EventoViewModel(get()) }
}

val webClientModule = module {
    single { EventoWebClient(get()) }
}
