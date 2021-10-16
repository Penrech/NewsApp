package com.enrech.articles.presentation.ui.article_detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enrech.articles.domain.usecase.GetArticleDetailUseCase
import com.enrech.articles.presentation.ui.article_detail.model.ArticleDetailsEmptyViews
import com.enrech.articles.presentation.ui.article_detail.state.ArticleDetailViewState
import com.enrech.core.data.response.Failure
import com.enrech.core.data.response.Result
import com.enrech.core.di.DispatchersModule.IO
import com.enrech.core.presentation.ui.empty_view.model.CoreErrorsEmptyViews
import com.enrech.core.presentation.ui.empty_view.model.EmptyVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val getArticleDetailUseCase: GetArticleDetailUseCase,
    @Named(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _viewState: MutableStateFlow<ArticleDetailViewState> =
        MutableStateFlow(ArticleDetailViewState.Loading)
    val viewState: StateFlow<ArticleDetailViewState> = _viewState

    fun loadDetailsWithId(articleId: Int) {
        _viewState.value = ArticleDetailViewState.Loading
        viewModelScope.launch(ioDispatcher) {
            when (val result = getArticleDetailUseCase(articleId)) {
                is Result.Success -> _viewState.value = ArticleDetailViewState.Success(result.data)
                is Result.Error -> _viewState.value = ArticleDetailViewState.Error(
                    handleFailure(
                        failure = result.failure,
                        retryId = articleId
                    )
                )
            }
        }
    }

    private fun handleFailure(failure: Failure, retryId: Int): EmptyVo =
        when (failure) {
            is Failure.ApiFailure.Network -> {
                CoreErrorsEmptyViews.NoNetworkConnection().apply {
                    retryCallback = { loadDetailsWithId(retryId) }
                }
            }
            is Failure.ApiFailure.Unknown -> {
                CoreErrorsEmptyViews.NetworkDatabase().apply {
                    retryCallback = { loadDetailsWithId(retryId) }
                }
            }
            is Failure.ApiFailure.NotFound -> ArticleDetailsEmptyViews.NotFound()
            else -> CoreErrorsEmptyViews.General()
        }

}