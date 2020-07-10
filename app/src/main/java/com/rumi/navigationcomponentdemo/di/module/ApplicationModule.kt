package com.rumi.navigationcomponentdemo.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rumi.navigationcomponentdemo.BuildConfig
import com.rumi.navigationcomponentdemo.data.remote.ApiService
import com.rumi.navigationcomponentdemo.data.remote.CartRemote
import com.rumi.navigationcomponentdemo.data.remote.CartRemoteImpl
import com.rumi.navigationcomponentdemo.data.remote.SkuRemote
import com.rumi.navigationcomponentdemo.data.remote.SkuRemoteImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class) //dependency provided here will be used across application
class ApplicationModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String, gson: Gson): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideSkuRemote(skuRemoteImpl: SkuRemoteImpl): SkuRemote = skuRemoteImpl

    @Provides
    @Singleton
    fun provideCartRemote(cartRemoteImpl: CartRemoteImpl): CartRemote = cartRemoteImpl
}