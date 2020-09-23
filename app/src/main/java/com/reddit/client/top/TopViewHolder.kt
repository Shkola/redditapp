package com.reddit.client.top

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reddit.R
import com.reddit.extension.setTextOrHide
import com.squareup.picasso.Picasso


class TopViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.title)
    private val description: TextView = view.findViewById(R.id.description)
    private val commentsCount: TextView = view.findViewById(R.id.commentsCount)
    private val image: ImageView = view.findViewById(R.id.image)
    private val saveBtn: TextView = view.findViewById(R.id.saveBtn)

    fun bind(item: TopRedditUi) {
        title.setTextOrHide(item.title)
        description.setTextOrHide(item.description)
        commentsCount.setTextOrHide(item.commentsCount)
        saveBtn.setOnClickListener {
            item.onSaveImage()
        }

        image.setOnClickListener {
            item.onOpenImage()
        }

        Picasso.get()
            .load(item.thumbnail)
            .into(image)
    }

    companion object {
        fun create(parent: ViewGroup): TopViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_reddit_list, parent, false)
            return TopViewHolder(view)
        }
    }
}