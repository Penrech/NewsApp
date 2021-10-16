package com.enrech.articles.presentation.ui.article_list.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enrech.articles.domain.usecase.GetArticleListUseCase
import com.enrech.articles.presentation.ui.article_list.model.ArticleListEmptyViews
import com.enrech.articles.presentation.ui.article_list.state.ArticleListViewState
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
class ArticleListViewModel @Inject constructor(
    private val getArticleListUseCase: GetArticleListUseCase,
    @Named(IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _viewState: MutableStateFlow<ArticleListViewState> = MutableStateFlow(ArticleListViewState.Loading)
    val viewState: StateFlow<ArticleListViewState> = _viewState

    init {
        loadArticleList()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun loadArticleList() {
        _viewState.value = ArticleListViewState.Loading
        viewModelScope.launch(ioDispatcher) {
            when (val result = getArticleListUseCase()) {
                is Result.Success -> {
                    when {
                        result.data.isEmpty() -> _viewState.value =
                            ArticleListViewState.ErrorOrEmpty(ArticleListEmptyViews.EmptyList())
                        else -> _viewState.value = ArticleListViewState.Success(result.data)
                    }
                }
                is Result.Error -> _viewState.value =
                    ArticleListViewState.ErrorOrEmpty(handleFailure(result.failure))
            }
        }
    }

    private fun handleFailure(failure: Failure): EmptyVo =
        when (failure) {
            is Failure.ApiFailure.Network -> {
                CoreErrorsEmptyViews.NoNetworkConnection().apply {
                    retryCallback = { loadArticleList() }
                }
            }
            is Failure.ApiFailure.Unknown -> {
                CoreErrorsEmptyViews.NetworkDatabase().apply {
                    retryCallback = { loadArticleList() }
                }
            }
            else -> {
                CoreErrorsEmptyViews.General()
            }
        }

}