package com.example.edward.pagingyoutube.api

import com.example.edward.pagingyoutube.utils.Constants
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


object APIService {

    val youtubeApi = createService(YoutubeApi::class.java)

    private fun <T> createService(cls: Class<T>): T {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()

        return retrofit.create(cls)
    }
}