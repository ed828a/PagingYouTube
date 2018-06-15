package com.example.edward.pagingyoutube.api

import com.example.edward.pagingyoutube.model.YoutubeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface YoutubeApi {
    @GET("search")
    fun searchVideo(@Query("q") query: String,
                    @Query("type") type: String,
                    @Query("key") key: String,
                    @Query("part") part: String,
                    @Query("maxResults") maxResults: String,
                    @Query("pageToken") pageToken: String): Call<YoutubeResponse>
}