package com.example.movieproject.di

import androidx.room.Room
import com.example.movieproject.data.datasource.remote.api.ApiService
import com.example.movieproject.data.datasource.remote.api.AuthInterceptor
import com.example.movieproject.data.datasource.remote.api.RetrofitClient
import com.example.movieproject.data.datasource.remote.interfaces.MovieDS
import com.example.movieproject.data.datasource.remote.remote_repository.MovieDSImpl
import com.example.movieproject.domain.repository.impl.MovieRepository
import com.example.movieproject.domain.repository.impl.MovieRepositoryImpl
import com.example.movieproject.domain.usecase.GetMovieCastUseCase
import com.example.movieproject.domain.usecase.GetNowShowingMovieUseCase
import com.example.movieproject.domain.usecase.GetPopularMovieUseCase
import com.example.movieproject.domain.usecase.GetTopRatedMovieUseCase
import com.example.movieproject.presentation.home.page_home.HomeViewModel
import com.example.movieproject.presentation.home.page_movieDetails.MovieDetailsViewModel
import com.example.movieproject.presentation.home.page_seeMore.SeeMoreViewModel
import com.example.movieproject.presentation.home.page_topRated.TopRatedViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { TopRatedViewModel(get()) }
    viewModel { SeeMoreViewModel(get(), get(), get()) }
    viewModel { MovieDetailsViewModel(get()) }
}
val useCaseModule = module {
    factory { GetNowShowingMovieUseCase(get()) }
    factory { GetPopularMovieUseCase(get()) }
    factory { GetTopRatedMovieUseCase(get()) }
    factory { GetMovieCastUseCase(get()) }
}
val repositoryModule = module {
    single {
        MovieRepositoryImpl(get(), get(),get()) as MovieRepository
    }
}
val dataSourceModule = module {
    single { MovieDSImpl(get()) as MovieDS }
}
val networkModule = module {
    single { AuthInterceptor() }
    single { RetrofitClient.provideOkHttpClient(get()) }
    single { RetrofitClient.provideRetrofit(get()) }
    single { ApiService.createService(get()) }
}
val databaseModule = module {
    // Provide the Room Database instance
    single {
        Room.databaseBuilder(
            androidContext(),
            com.example.myroomdatabase.Database.MovieDatabase::class.java,
            "movie_database"
        ).build()
    }

    // Provide the MovieDao instance from the database
    single { get<com.example.myroomdatabase.Database.MovieDatabase>().movieDao() }
    single { get<com.example.myroomdatabase.Database.MovieDatabase>().movieCastDao() }
}