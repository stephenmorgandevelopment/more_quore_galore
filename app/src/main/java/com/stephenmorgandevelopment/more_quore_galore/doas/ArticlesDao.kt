package com.stephenmorgandevelopment.more_quore_galore.doas

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stephenmorgandevelopment.more_quore_galore.models.Article
import com.stephenmorgandevelopment.more_quore_galore.models.Client

@Dao
interface ArticlesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    // TODO Insert multi row into DB.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(article: List<Article>)

   @Query("DELETE FROM Article")
    suspend fun deleteAll()


    // TODO Load all articles from DB.
    @Query("SELECT * from Article")
    fun loadAll(): LiveData<List<Article>>

    suspend fun updateDatabase(articles: List<Article>) {
        deleteAll()
        insertAll(articles)
    }


}