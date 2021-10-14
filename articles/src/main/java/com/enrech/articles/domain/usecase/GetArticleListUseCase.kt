package com.enrech.articles.domain.usecase

import com.enrech.articles.data.repository.ArticlesRepository
import com.enrech.articles.presentation.model.SimpleArticleVo
import com.enrech.core.data.response.Result
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetArticleListUseCase @Inject constructor(private val articlesRepository: ArticlesRepository) {

    suspend operator fun invoke(): Result<List<SimpleArticleVo>> = articlesRepository.getArticlesList()
}