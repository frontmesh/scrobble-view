package com.frontmatic.scrobbleview.di

import androidx.paging.ExperimentalPagingApi
import com.frontmatic.scrobbleview.BuildConfig
import com.frontmatic.scrobbleview.data.ScrobbleDatabase
import com.frontmatic.scrobbleview.data.api.LastFMApi
import com.frontmatic.scrobbleview.data.repository.RemoteDataSource
import com.frontmatic.scrobbleview.data.repository.impl.RemoteDataSourceImpl
import com.frontmatic.scrobbleview.util.Constants.BASE_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalPagingApi
@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideBodyInterceptor(): HttpLoggingInterceptor {
        val httpInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return httpInterceptor
    }

    @Provides
    @Singleton
    fun provideHttpClient(bodyInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addNetworkInterceptor(bodyInterceptor)
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .url(
                        chain.request().url.newBuilder()
                            .addQueryParameter("api_key", BuildConfig.API_KEY)
                            .addQueryParameter("format", "json")
                            .build()
                    )
                    .method(original.method, original.body)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(
                GsonBuilder()
                    .disableHtmlEscaping()
                    .create()
            ))
            .build()
    }

    @Provides
    @Singleton
    fun provideLastFMApi(retrofit: Retrofit): LastFMApi {
        return retrofit.create(LastFMApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(api: LastFMApi, database:ScrobbleDatabase): RemoteDataSource {
        return RemoteDataSourceImpl(api, database)
    }
}