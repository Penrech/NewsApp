package com.enrech.core.presentation.ui.empty_view.model

interface EmptyVoEnum {
    val imageId: Int
    val titleId: Int
    val descriptionId: Int?
    val retryButtonEnabled: Boolean
    val retryButtonCustomTitleId: Int?
    var retryCallback: (() -> Unit)?
}