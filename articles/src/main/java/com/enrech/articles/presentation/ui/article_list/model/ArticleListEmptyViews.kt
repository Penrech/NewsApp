package com.enrech.articles.presentation.ui.article_list.model

import com.enrech.articles.R
import com.enrech.core.presentation.ui.empty_view.model.EmptyVo

sealed class ArticleListEmptyViews: EmptyVo() {
    data class EmptyList(
        override val imageId: Int = R.drawable.ic_twotone_article_24,
        override val titleId: Int = R.string.empty_article_list_error_title,
        override val retryButtonEnabled: Boolean = false
    ): ArticleListEmptyViews()
}
