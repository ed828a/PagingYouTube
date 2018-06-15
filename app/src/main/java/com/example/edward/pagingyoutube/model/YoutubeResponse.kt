package com.example.edward.pagingyoutube.model

import com.google.gson.annotations.SerializedName


class YoutubeResponse {
    val kind: String? = null
    val etag: String? = null
    val nextPageToken: String? = null
    val pageInfo: PageInfo? = null
    val items: List<Item> = ArrayList()

    class PageInfo {
        val totalResults: Int = 0
        val resultsPerPage: Int = 0
    }

    class Item {
        val kind: String? = null
        val etag: String? = null
        val id: ID? = null
        val snippet: Snippet? = null

        class Snippet {
            val publishedAt: String? = null
            val channelId: String? = null
            val title: String? = null
            val description: String? = null
            val channelTitle: String? = null
            val categoryId: String? = null
            val liveBroadcastContent: String? = null
            val thumbnails: Thumbnails? = null
            val tags: List<String> = ArrayList()

            class Thumbnails {
                @SerializedName("default")
                val default: Default? = null
                val high: High? = null

                class Default {
                    val url: String? = null
                    val width: Int = 0
                    val height: Int = 0
                }

                class High {
                    val url: String? = null
                    val width: Int = 0
                    val height: Int = 0
                }
            }
        }

        class ID {
            val kind: String? = null
            val videoId: String? = null
        }
    }
}