package com.enrech.articles.data.model

import com.google.gson.annotations.SerializedName

data class ArticleListRawResponse(
    @SerializedName("items") val listOfArticles: List<ArticleListItemResponse>
)
