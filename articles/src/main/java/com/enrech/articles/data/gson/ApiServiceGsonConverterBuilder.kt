package com.enrech.articles.data.gson

import com.enrech.articles.data.model.ArticleDetailResponse
import com.enrech.articles.data.model.ArticleListItemResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class ApiServiceGsonConverterBuilder {

    private companion object {
        const val FETCH_ARTICLES_LIST_PARENT_OBJECT = "items"
        const val FETCH_ARTICLE_DETAIL_PARENT_OBJECT = "item"
    }

    fun build(): Gson =
        GsonBuilder()
            .registerTypeAdapter(
                TypeToken.getParameterized(
                    List::class.java,
                    ArticleListItemResponse::class.java
                ).type,
                ApiServiceDeserializer<List<ArticleListItemResponse>>(FETCH_ARTICLES_LIST_PARENT_OBJECT)
            )
            .registerTypeAdapter(
                ArticleDetailResponse::class.java,
                ApiServiceDeserializer<ArticleDetailResponse>(FETCH_ARTICLE_DETAIL_PARENT_OBJECT)
            )
            .create()

}