package com.enrech.articles.data.datasource

import com.enrech.articles.data.model.ArticleDetailResponse
import com.enrech.articles.data.model.ArticleListItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticlesApiService {

    @GET("/contentList.json")
    suspend fun fetchArticlesList(): Response<List<ArticleListItemResponse>>

    @GET("/content/{id}.json")
    suspend fun fetchArticleDetail(@Path("id") articleId: Int): Response<ArticleDetailResponse>
}