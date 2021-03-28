package com.corootine.fuzzy.network.di

import android.content.Context
import com.corootine.fuzzy.BuildConfig
import com.corootine.fuzzy.network.retrofit.RequestExecutor
import com.corootine.fuzzy.network.retrofit.RetrofitRequestExecutor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val trustManagerFactory = TrustManagerFactory(context)

        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
//            .hostnameVerifier { hostname, _ -> hostname == BuildConfig.SERVER_HOSTNAME.split(":")[0] }
//            .sslSocketFactory(
//                trustManagerFactory.sslSocketFactory,
//                trustManagerFactory.x509TrustManager
//            )

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor().apply { level = Level.BODY }
            client.addInterceptor(interceptor)
        }

        return client.build()
    }

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://${BuildConfig.SERVER_HOSTNAME}")
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Binds
    abstract fun provideRetrofitRequestExecutor(retrofitRequestExecutor: RetrofitRequestExecutor): RequestExecutor
}