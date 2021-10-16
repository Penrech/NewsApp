package com.enrech.articles.presentation.ui.article_list.state

import com.enrech.articles.presentation.ui.article_list.model.SimpleArticleVo
import com.enrech.core.data.response.Failure

sealed class ArticleListViewState {
    object Loading: ArticleListViewState()
    object EmptyList: ArticleListViewState()
    data class Success(val data: List<SimpleArticleVo>): ArticleListViewState()
    data class GeneralError(val failure: Failure): ArticleListViewState()
    data class SecondaryError(val failureMessage: Failure): ArticleListViewState()
}
