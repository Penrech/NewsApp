package com.enrech.articles.data.datasource

import com.enrech.articles.data.model.ArticleDetailResponse
import com.enrech.articles.data.model.ArticleListRawResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticlesApiService {

    @GET("contentList.json")
    suspend fun fetchArticlesList(): Response<ArticleListRawResponse>

    @GET("content/{id}.json")
    suspend fun fetchArticleDetail(@Path("id") articleId: Int): Response<ArticleDetailResponse>
}