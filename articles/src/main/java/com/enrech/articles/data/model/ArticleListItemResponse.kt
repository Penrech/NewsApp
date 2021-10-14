package com.enrech.articles.data.model

import com.google.gson.annotations.SerializedName

data class ArticleListItemResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String,
    @SerializedName("date") val date: String
)