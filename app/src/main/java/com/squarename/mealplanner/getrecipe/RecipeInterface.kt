package com.squarename.mealplanner.getrecipe

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeInterface {
    // test用
    @GET("v2/items.json")
    fun items(): Call<List<Item>>

    @GET("searchRecipe_api.php")
    fun recipes(@Query("temp") temp: String): Call<List<Recipe>>
}

fun createService(): RecipeInterface {
    // test用
//    val baseApiUrl = "https://qiita.com/api/"
    val baseApiUrl = "http://59.106.222.80/"

    val httpLogging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val httpClientBuilder = OkHttpClient.Builder().addInterceptor(httpLogging)

    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseApiUrl)
        .client(httpClientBuilder.build())
        .build()

    return retrofit.create(RecipeInterface::class.java)
}