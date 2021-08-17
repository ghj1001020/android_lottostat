package com.ghj.lottostat.util

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException

object JsonUtil {

    // dto -> json string
    fun <T> dtoToJsonString( dto : T? ) : String {
        if( dto == null ) {
            return ""
        }

        val builder : GsonBuilder = GsonBuilder()
        builder.excludeFieldsWithoutExposeAnnotation()
        builder.serializeNulls()

        val gson : Gson = builder.create()
        return gson.toJson( dto )
    }

    // json string -> dto
    fun <T> parseJson( json : String? , dto : Class<T> ) : T? {
        if( TextUtils.isEmpty(json) || "{}" == json ) {
            return null
        }

        try {
            val builder : GsonBuilder = GsonBuilder()
            builder.excludeFieldsWithoutExposeAnnotation()
            builder.serializeNulls()

            val gson : Gson = builder.create()
            return gson.fromJson( json , dto )
        }
        catch ( e : JsonSyntaxException) {
            return null
        }
    }
}