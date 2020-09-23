package com.reddit.client.top

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.reddit.R
import com.reddit.extension.setTextOrHide

class TopLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<TopLoadStateAdapter.TopLoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): TopLoadStateViewHolder =
        TopLoadStateViewHolder.create(parent, retry)

    override fun onBindViewHolder(holder: TopLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class TopLoadStateViewHolder(
        view: View,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val errorTv: TextView = view.findViewById(R.id.errorTv)
        private val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        private val retryBtn: Button = view.findViewById(R.id.retryBtn)

        init {
            retryBtn.setOnClickListener {
                retry()
            }
        }

        fun bind(loadState: LoadState) {
            errorTv.isVisible = loadState !is LoadState.Loading

            if (loadState is LoadState.Error) {
                errorTv.setTextOrHide(loadState.error.localizedMessage ?: loadState.error.message)
            }
            progressBar.isVisible = loadState is LoadState.Loading
            retryBtn.isVisible = loadState !is LoadState.Loading
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): TopLoadStateViewHolder = TopLoadStateViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.view_reddit_load, parent, false), retry)
        }
    }
}
