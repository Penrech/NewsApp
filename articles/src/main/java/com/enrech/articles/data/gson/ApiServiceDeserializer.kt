package com.enrech.articles.data.gson

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ApiServiceDeserializer<T>(private val parentObject: String): JsonDeserializer<T> {

    override fun deserialize(
        jsonElement: JsonElement?,
        type: Type?,
        context: JsonDeserializationContext?
    ): T = Gson().fromJson(jsonElement?.asJsonObject?.get(parentObject), type)
}