package com.enrech.articles.domain.usecase

import com.enrech.articles.data.repository.ArticlesRepository
import com.enrech.articles.presentation.model.DetailedArticleVo
import com.enrech.core.data.response.Result
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetArticleDetailUseCase @Inject constructor(private val articlesRepository: ArticlesRepository) {

    suspend operator fun invoke(articleId: Int): Result<DetailedArticleVo> =
        articlesRepository.getArticleDetail(articleId)
}