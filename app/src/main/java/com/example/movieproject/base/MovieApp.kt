package com.example.movieproject.base

import android.app.Application
import com.example.movieproject.di.dataSourceModule
import com.example.movieproject.di.databaseModule
import com.example.movieproject.di.networkModule
import com.example.movieproject.di.repositoryModule
import com.example.movieproject.di.useCaseModule
import com.example.movieproject.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this)
    }

    private fun startKoin(app: Application) {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(app)
            modules(
                databaseModule,
                networkModule,
                dataSourceModule,
                repositoryModule,
                useCaseModule,
                viewModelModule,

            )
        }

    }
}