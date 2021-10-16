package com.enrech.articles.domain.mapper

import com.enrech.articles.data.model.ArticleDetailResponse
import com.enrech.articles.presentation.ui.article_detail.model.DetailedArticleVo
import javax.inject.Inject

class ArticleDetailMapper @Inject constructor() {

    fun toVo(dto: ArticleDetailResponse): DetailedArticleVo =
        DetailedArticleVo(
            id = dto.id,
            title = dto.title,
            subtitle = dto.subtitle,
            body = dto.body,
            date = dto.date
        )
}