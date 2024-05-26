package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.di

import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.Api
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.AuthorizationInterceptor
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core.util.constants.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun okHttpLogging(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return logging
    }

    @Provides
    @Singleton
    fun okHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor,
    ) = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(authorizationInterceptor)
        .build()

    @Provides
    @Singleton
    fun networkService(
        okHttpClient: OkHttpClient,
        moshiBuilder: Moshi
    ): Api =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshiBuilder))
            .build()
            .create(Api::class.java)

    @Provides
    @Singleton
    fun moshiBuilder(): Moshi.Builder = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
}
