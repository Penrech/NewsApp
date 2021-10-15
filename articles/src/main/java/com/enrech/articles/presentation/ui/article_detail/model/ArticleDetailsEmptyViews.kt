package com.enrech.articles.presentation.ui.article_detail.model

import com.enrech.articles.R
import com.enrech.core.presentation.ui.empty_view.model.EmptyVo

sealed class ArticleDetailsEmptyViews: EmptyVo() {
    data class NotFound(
        override val imageId: Int = R.drawable.ic_round_link_off_24,
        override val titleId: Int = R.string.not_found_article_detail_error_title,
        override val retryButtonEnabled: Boolean = false
    ): ArticleDetailsEmptyViews()
}