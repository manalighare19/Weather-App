package com.example.weatherapp.di

import com.example.weatherapp.network.ApiClient
import com.example.weatherapp.network.ApiService
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return ApiClient.buildService(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(impl: WeatherRepositoryImpl): WeatherRepository {
        return impl
    }

    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}