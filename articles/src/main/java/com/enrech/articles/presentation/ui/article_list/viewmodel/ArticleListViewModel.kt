package com.enrech.articles.presentation.ui.article_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enrech.articles.domain.usecase.GetArticleListUseCase
import com.enrech.articles.presentation.ui.article_list.state.ArticleListViewState
import com.enrech.core.data.response.Result
import com.enrech.core.di.DispatchersModule.IO
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

    fun loadArticleList() {
        viewModelScope.launch(ioDispatcher) {
            when(val result = getArticleListUseCase()) {
                is Result.Success -> {
                    when {
                        result.data.isEmpty() -> _viewState.value = ArticleListViewState.EmptyList
                        else -> _viewState.value = ArticleListViewState.Success(result.data)
                    }
                }
                is Result.Error -> _viewState.value = ArticleListViewState.GeneralError(result.failure)
            }
        }
    }

}