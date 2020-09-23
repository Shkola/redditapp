package com.reddit.client.top

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

class TopAdapter : PagingDataAdapter<TopRedditUi, TopViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopViewHolder = TopViewHolder.create(parent)

    override fun onBindViewHolder(holder: TopViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TopRedditUi>() {
            override fun areItemsTheSame(oldItem: TopRedditUi, newItem: TopRedditUi): Boolean = oldItem.title == newItem.title && oldItem.description == newItem.description

            override fun areContentsTheSame(oldItem: TopRedditUi, newItem: TopRedditUi): Boolean =
                oldItem == newItem
        }
    }
}