package com.reddit.client.top

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.reddit.R
import com.reddit.extension.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class TopFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: TopViewModel by viewModels()

    private val adapter by lazy { TopAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe(viewModel.pagingTopListLiveData) { data ->
            adapter.submitData(
                lifecycle = viewLifecycleOwner.lifecycle,
                pagingData = data
            )
        }
    }
}