package com.enrech.core.presentation.ui.empty_view.model

import com.enrech.core.R

sealed class CoreErrorsEmptyViews: EmptyVo() {

    data class General(
        override val imageId: Int = R.drawable.ic_twotone_error_24,
        override val titleId: Int = R.string.general_error_title,
        override val retryButtonEnabled: Boolean = false
    ): CoreErrorsEmptyViews()

    data class NoNetworkConnection(
        override val imageId: Int = R.drawable.ic_twotone_cloud_off_24,
        override val titleId: Int = R.string.network_connection_error_title,
        override val retryButtonEnabled: Boolean = true,
        override val descriptionId: Int? = R.string.network_connection_error_subtitle
    ): CoreErrorsEmptyViews()

    data class NetworkDatabase(
        override val imageId: Int = R.drawable.ic_twotone_cloud_off_24,
        override val titleId: Int = R.string.network_database_error_title,
        override val retryButtonEnabled: Boolean = true,
        override val descriptionId: Int? = R.string.network_database_error_subtitle
    ): CoreErrorsEmptyViews()
}
