package com.stephenmorgandevelopment.more_quore_galore.api

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(Application::class)
object NewsModule {
    @Provides
    @Singleton
    fun provideNewsService(): NewsService {
        val newsClient = Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return newsClient.create(NewsService::class.java)
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(
        @ApplicationContext context: Context
    ): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}