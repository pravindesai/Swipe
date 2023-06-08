package com.pravin.desai.swipe.di

import com.pravin.desai.swipe.BuildConfig
import com.pravin.desai.swipe.network.retrofit.ProductService
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
object AppModule {

    @Provides
    @Singleton
    fun providesHttpInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor(
        HttpLoggingInterceptor.Logger.DEFAULT
    ).apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun providesOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder().method(
                original.method,
                original.body
            )
            chain.proceed(builder.build())
        }.addNetworkInterceptor(httpLoggingInterceptor).build()

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient:OkHttpClient):Retrofit{
        return Retrofit.Builder().baseUrl(BuildConfig.SWIPE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providedProductApiService(retrofit:Retrofit):ProductService{
        return retrofit.create(ProductService::class.java)
    }

}