package com.example.edward.pagingyoutube.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.edward.pagingyoutube.R
import com.example.edward.pagingyoutube.model.YoutubeResponse
import com.example.edward.pagingyoutube.utils.GlideApp
import kotlinx.android.synthetic.main.adapter_item_video.view.*


class VideosAdapter : RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {
    private val items = arrayListOf<YoutubeResponse.Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val snippet = items[position].snippet
        val url = snippet!!.thumbnails!!.default!!.url

        GlideApp.with(holder.itemView.context).load(url).centerCrop().into(holder.logo)

        holder.title.text = snippet.title
        holder.description.text = snippet.description
        holder.date.text = snippet.publishedAt
    }

    override fun getItemCount()= items.count()

    fun addAll(items: Collection<YoutubeResponse.Item>) {
        val currentItemCount = this.items.size
        this.items.addAll(items)
        notifyItemRangeInserted(currentItemCount, items.size)
    }

    fun replaceWith(items: Collection<YoutubeResponse.Item>?) {
        if (items != null) {
            val oldCount = this.items.size
            val newCount = items.size
            val delCount = oldCount - newCount
            this.items.clear()
            this.items.addAll(items)
            when {
                delCount > 0 -> {
                    notifyItemRangeChanged(0, newCount)
                    notifyItemRangeRemoved(newCount, delCount)
                }
                delCount < 0 -> {
                    notifyItemRangeChanged(0, oldCount)
                    notifyItemRangeInserted(oldCount, -delCount)
                }
                else -> notifyItemRangeChanged(0, newCount)
            }
        }

    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var logo: ImageView = itemView.logo
        var title: TextView = itemView.title
        var description: TextView = itemView.description
        var date: TextView = itemView.date
    }
}