package com.enrech.articles.di

import com.enrech.articles.data.datasource.ArticlesApiService
import com.enrech.articles.data.gson.ApiServiceGsonConverterBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ArticlesApiModule {

    private const val BASE_URL = "https://dynamic.pulselive.com/test/native/"

    @Singleton
    @Provides
    fun provideArticlesGsonDeserializer(): GsonConverterFactory =
        GsonConverterFactory.create(ApiServiceGsonConverterBuilder().build())

    @Singleton
    @Provides
    fun provideArticlesApiService(gsonConverterFactory: GsonConverterFactory): ArticlesApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(ArticlesApiService::class.java)
}