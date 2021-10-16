package com.enrech.articles.data.datasource

import com.enrech.articles.data.model.ArticleDetailResponse
import com.enrech.articles.data.model.ArticleListItemResponse
import com.enrech.core.data.datasource.remote.ApiHandler
import com.enrech.core.data.datasource.remote.ApiHandlerImpl
import com.enrech.core.data.response.Result
import javax.inject.Inject

class ArticlesApiDatasource @Inject constructor(
    private val apiService: ArticlesApiService,
    apiHandlerImpl: ApiHandlerImpl
) : ApiHandler by apiHandlerImpl {

    suspend fun getArticlesList(): Result<List<ArticleListItemResponse>> =
        fetchApiResponse { apiService.fetchArticlesList() }

    suspend fun getArticleDetail(articleId: Int): Result<ArticleDetailResponse> =
        fetchApiResponse { apiService.fetchArticleDetail(articleId) }
}