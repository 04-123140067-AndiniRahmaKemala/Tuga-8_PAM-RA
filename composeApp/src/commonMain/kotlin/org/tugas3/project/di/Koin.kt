package org.tugas3.project.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel
import org.tugas3.project.data.NoteRepository
import org.tugas3.project.data.SettingsManager
import org.tugas3.project.viewmodel.NotesViewModel
import org.tugas3.project.viewmodel.ProfileViewModel
import org.tugas3.project.db.NoteDatabase
import org.tugas3.project.getDeviceInfo
import org.tugas3.project.getNetworkMonitor
import org.tugas3.project.data.createDataStore
import org.tugas3.project.data.DatabaseDriverFactory

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule)
    }

// for iOS
fun initKoin() = initKoin {}

val commonModule = module {
    single { 
        val driverFactory = get<DatabaseDriverFactory>()
        NoteDatabase(driverFactory.createDriver())
    }
    single { NoteRepository(get()) }
    single { 
        val context = getOrNull<Any>()
        SettingsManager(createDataStore(context))
    }
    single { getDeviceInfo() }
    single { 
        val context = getOrNull<Any>()
        getNetworkMonitor(context) 
    }
    
    viewModel { NotesViewModel(get(), get(), get()) }
    viewModel { ProfileViewModel(get(), get()) }
}
