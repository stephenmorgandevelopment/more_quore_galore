package com.stephenmorgandevelopment.more_quore_galore.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stephenmorgandevelopment.more_quore_galore.doas.ArticlesDao
import com.stephenmorgandevelopment.more_quore_galore.models.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class ArticlesDatabase : RoomDatabase() {
    abstract fun articleDao() : ArticlesDao
}