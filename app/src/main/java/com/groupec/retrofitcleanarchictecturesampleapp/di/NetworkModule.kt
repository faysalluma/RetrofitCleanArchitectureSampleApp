package com.groupec.retrofitcleanarchictecturesampleapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
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

    // Custom my gson to format date
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        httpClient: OkHttpClient,
        gson: Gson // Inject the custom Gson instance
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
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