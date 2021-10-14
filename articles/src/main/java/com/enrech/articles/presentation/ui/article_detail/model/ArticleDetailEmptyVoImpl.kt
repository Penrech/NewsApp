package com.enrech.articles.presentation.ui.article_detail.model

import com.enrech.articles.R
import com.enrech.core.presentation.ui.empty_view.model.EmptyVoEnum
import com.enrech.core.R as coreR

enum class ArticleDetailEmptyVoImpl(
    override val imageId: Int,
    override val titleId: Int,
    override val descriptionId: Int? = null,
    override val retryButtonEnabled: Boolean,
    override val retryButtonCustomTitleId: Int? = null,
    override var retryCallback: (() -> Unit)? = null
): EmptyVoEnum {
    ARTICLE_NOT_FOUND_ERROR(
        imageId = R.drawable.ic_round_link_off_24,
        titleId = coreR.string.not_found_article_detail_error_title,
        retryButtonEnabled = false
    )
}