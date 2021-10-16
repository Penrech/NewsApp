package com.enrech.articles.data.repository

import com.enrech.articles.data.datasource.ArticlesApiDatasource
import com.enrech.articles.domain.mapper.ArticleDetailMapper
import com.enrech.articles.domain.mapper.ArticleListMapper
import com.enrech.articles.presentation.ui.article_detail.model.DetailedArticleVo
import com.enrech.articles.presentation.ui.article_list.model.SimpleArticleVo
import com.enrech.core.data.response.Result
import javax.inject.Inject

class ArticlesRepository @Inject constructor(
    private val articlesApiDatasource: ArticlesApiDatasource,
    private val articleListMapper: ArticleListMapper,
    private val articleDetailMapper: ArticleDetailMapper
) {

    suspend fun getArticlesList(): Result<List<SimpleArticleVo>> =
        when(val result = articlesApiDatasource.getArticlesList()) {
            is Result.Success -> Result.Success(articleListMapper.toVo(result.data.listOfArticles))
            is Result.Error -> result
        }

    suspend fun getArticleDetail(articleId: Int): Result<DetailedArticleVo> =
        when(val result = articlesApiDatasource.getArticleDetail(articleId)) {
            is Result.Success -> Result.Success(articleDetailMapper.toVo(result.data))
            is Result.Error -> result
        }
}