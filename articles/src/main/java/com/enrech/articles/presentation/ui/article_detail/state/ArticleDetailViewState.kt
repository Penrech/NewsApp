package com.enrech.articles.presentation.ui.article_detail.state

import com.enrech.articles.presentation.ui.article_detail.model.DetailedArticleVo
import com.enrech.core.presentation.ui.empty_view.model.EmptyVo

sealed class ArticleDetailViewState {
    object Loading: ArticleDetailViewState()
    data class Success(val data: DetailedArticleVo): ArticleDetailViewState()
    data class Error(val emptyVo: EmptyVo): ArticleDetailViewState()
}
