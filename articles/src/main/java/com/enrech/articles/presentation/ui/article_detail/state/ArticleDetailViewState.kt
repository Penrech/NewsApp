package com.enrech.articles.presentation.ui.article_detail.state

import com.enrech.articles.presentation.model.DetailedArticleVo
import com.enrech.core.data.response.Failure

sealed class ArticleDetailViewState {
    object Loading: ArticleDetailViewState()
    data class Success(val data: DetailedArticleVo): ArticleDetailViewState()
    data class Error(val failure: Failure): ArticleDetailViewState()
}
