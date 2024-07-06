package com.groupec.retrofitcleanarchictecturesampleapp.di

import com.groupec.retrofitcleanarchictecturesampleapp.remote.common.Constants
import com.groupec.retrofitcleanarchictecturesampleapp.remote.repository.RemoteRepository
import com.groupec.retrofitcleanarchictecturesampleapp.remote.repository.RemoteRepositoryImpl
import com.groupec.retrofitcleanarchictecturesampleapp.remote.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(
        httpClient: OkHttpClient
    ) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Provides
    @Singleton
    fun providerRepository(
        apiService: ApiService
    ) : RemoteRepository {
        return RemoteRepositoryImpl(apiService)
    }

}