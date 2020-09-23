package com.reddit.client.top

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

        observe(viewModel.actionMessage) { message ->
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }

        observe(viewModel.actionOpenImage) { imageUrl ->
            val intent = Intent(ACTION_VIEW).apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                setDataAndType(Uri.parse(imageUrl), "image/*")
            }
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(activity, getString(R.string.unable_open), Toast.LENGTH_SHORT).show()
            }
        }
    }
}