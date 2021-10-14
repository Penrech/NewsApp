package com.enrech.articles.presentation.ui.article_detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enrech.articles.domain.usecase.GetArticleDetailUseCase
import com.enrech.articles.presentation.ui.article_detail.state.ArticleDetailViewState
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
class ArticleDetailViewModel @Inject constructor(
    private val getArticleDetailUseCase: GetArticleDetailUseCase,
    @Named(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _viewState: MutableStateFlow<ArticleDetailViewState> =
        MutableStateFlow(ArticleDetailViewState.Loading)
    val viewState: StateFlow<ArticleDetailViewState> = _viewState

    fun loadDetailsWithId(articleId: Int) {
        viewModelScope.launch(ioDispatcher) {
            when(val result = getArticleDetailUseCase(articleId)) {
                is Result.Success -> _viewState.value = ArticleDetailViewState.Success(result.data)
                is Result.Error -> _viewState.value = ArticleDetailViewState.Error(result.failure)
            }
        }
    }

}