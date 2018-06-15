package com.example.edward.pagingyoutube

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.edward.pagingyoutube.adapters.VideosAdapter
import com.example.edward.pagingyoutube.api.APIService
import com.example.edward.pagingyoutube.model.YoutubeResponse
import com.example.edward.pagingyoutube.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private var adapter: VideosAdapter? = null
    //if you are using searchview to get search result then store searched query in lastSearched variable.
    //get latest token and store in lastToken variable.
    private var lastSearched: String? = ""
    private var lastToken: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.video_list)


        adapter = VideosAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter

        //load data from api.
        search("", false)

        buttonMore.setOnClickListener {
            //load more data
            search("", true)
        }
    }

    /**
     * call this method to get response from youtube API.
     *
     * @param query String value to search on google, Empty string means get all videos.
     * @param more  if you want to load next page then pass true, this means add new items at bottom of RecyclerView.
     */
    private fun search(query: String?, more: Boolean) {
        var query = query
        val progressDialog = ProgressDialog.show(this, null, "Loading ...", true, false)
        var searchType = "video"
        if (query != null) {
            if (query.startsWith("#")) {
                searchType = "video"
                query = query.substring(1)
            } else if (query.startsWith("@")) {
                searchType = "channel"
                query = query.substring(1)
            }
        }
        if (!more) {
            lastSearched = query
            lastToken = ""
        }

        val youtubeResponseCall = APIService.youtubeApi.searchVideo(query!!, searchType, Constants.YOUTUBE_API_KEY, "snippet,id", "10", lastToken!!)
        youtubeResponseCall.enqueue(object : Callback<YoutubeResponse> {
           override fun onResponse(call: Call<YoutubeResponse>, response: Response<YoutubeResponse>) {
                if (progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
                val body = response.body()
               Log.d(TAG, response.body().toString())

                if (body != null) {
                    val items = body!!.items
                    lastToken = body!!.nextPageToken
                    if (more) {
                        adapter!!.addAll(items)
                    } else {
                        adapter!!.replaceWith(items)
                    }
                }
            }

            override fun onFailure(call: Call<YoutubeResponse>, t: Throwable) {
                if (progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
                Log.e(TAG, "onFailure: ", t)
            }
        })

    }

    companion object {
        private val TAG = "MainActivity"
    }
}
