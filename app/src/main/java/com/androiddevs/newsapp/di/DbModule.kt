package com.androiddevs.newsapp.di

import android.content.Context
import androidx.room.Room
import com.androiddevs.newsapp.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DbModule {

    @Provides
    fun provideArticleDatabase(@ApplicationContext context: Context): ArticleDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ArticleDatabase::class.java,
            "article_db.db"
        ).build()
    }

    /*@Provides
    fun provideArticleDao(database: ArticleDatabase): ArticleDao {
        return database.getArticleDao()
    }*/
}