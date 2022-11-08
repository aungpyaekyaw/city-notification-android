package com.nanoware.pakokkunotification.injection

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.nanoware.pakokkunotification.core.extension.Preferences
import com.nanoware.pakokkunotification.data.remote.BackendApi
import com.nanoware.pakokkunotification.data.remote.FirebaseCloudMessagingApi
import com.nanoware.pakokkunotification.data.repository.BackendRepository
import com.nanoware.pakokkunotification.data.repository.FirebaseCloudMessagingRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideFirebaseCloudMessagingApi(factory: GsonConverterFactory): FirebaseCloudMessagingApi =
        Retrofit.Builder()
            .baseUrl(FirebaseCloudMessagingApi.BASE_URL)
            .addConverterFactory(factory)
            .build()
            .create(FirebaseCloudMessagingApi::class.java)

    @Provides
    @Singleton
    fun provideFirebaseCloudMessagingRepository(api: FirebaseCloudMessagingApi): FirebaseCloudMessagingRepository =
        FirebaseCloudMessagingRepository(api)

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences = app.getSharedPreferences(
        Preferences.PREFS, Context.MODE_PRIVATE)


    @Provides
    @Singleton
    fun provideBackendApi(factory: GsonConverterFactory): BackendApi =
        Retrofit.Builder()
            .baseUrl(BackendApi.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                    )
                    .build()
            )
            .addConverterFactory(factory)
            .build()
            .create(BackendApi::class.java)


    @Provides
    @Singleton
    fun provideBackendRepository(api: BackendApi): BackendRepository =
        BackendRepository(api)
}