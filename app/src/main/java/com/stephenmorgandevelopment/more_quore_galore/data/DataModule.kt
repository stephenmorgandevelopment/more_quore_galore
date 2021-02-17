package com.stephenmorgandevelopment.more_quore_galore.data

import android.content.Context
import androidx.room.Room
import com.stephenmorgandevelopment.more_quore_galore.doas.ArticlesDao
import com.stephenmorgandevelopment.more_quore_galore.doas.ClientDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideArticleDatabase(@ApplicationContext context: Context): ArticlesDatabase =
        Room.databaseBuilder(context, ArticlesDatabase::class.java, "articles.db")
            .build()

    @Provides
    @Singleton
    fun provideArticleDao(articlesDatabase: ArticlesDatabase): ArticlesDao =
        articlesDatabase.articleDao()

}