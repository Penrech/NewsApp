package com.enrech.articles.presentation.ui.article_list.state

import com.enrech.articles.presentation.ui.article_list.model.SimpleArticleVo
import com.enrech.core.presentation.ui.empty_view.model.EmptyVo

sealed class ArticleListViewState {
    object Loading: ArticleListViewState()
    data class Success(val data: List<SimpleArticleVo>): ArticleListViewState()
    data class ErrorOrEmpty(val emptyVo: EmptyVo): ArticleListViewState()
}
