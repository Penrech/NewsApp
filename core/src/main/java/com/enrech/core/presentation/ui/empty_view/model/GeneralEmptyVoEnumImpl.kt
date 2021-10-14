package com.enrech.core.presentation.ui.empty_view.model

import com.enrech.core.R

enum class GeneralEmptyVoEnumImpl(
    override val imageId: Int,
    override val titleId: Int,
    override val descriptionId: Int? = null,
    override val retryButtonEnabled: Boolean,
    override val retryButtonCustomTitleId: Int? = null,
    override var retryCallback: (() -> Unit)? = null
): EmptyVoEnum {
    GENERAL_ERROR(
        imageId = R.drawable.ic_twotone_error_24,
        titleId = R.string.general_error_title,
        retryButtonEnabled = false
    )
}