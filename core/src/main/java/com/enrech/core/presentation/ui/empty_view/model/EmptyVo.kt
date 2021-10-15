package com.enrech.core.presentation.ui.empty_view.model

abstract class EmptyVo {
    abstract val imageId: Int
    abstract val titleId: Int
    open val descriptionId: Int? = null
    abstract val retryButtonEnabled: Boolean
    open val retryButtonCustomTitleId: Int? = null
    open var retryCallback: (() -> Unit)? = null
}