package com.reddit.client.top

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.reddit.R
import com.reddit.extension.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class TopFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: TopViewModel by viewModels()

    private val adapter by lazy { TopAdapter() }

    private val permissionLaunchers = mutableListOf<ActivityResultLauncher<String>>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = TopLoadStateAdapter { adapter.retry() },
            footer = TopLoadStateAdapter { adapter.retry() }
        )
        retryBtn.setOnClickListener { adapter.retry() }

        swipeContainer.setOnRefreshListener {
            adapter.refresh()
        }

        adapter.addLoadStateListener { loadState ->
            recyclerView.isVisible = loadState.source.refresh !is LoadState.Error
            swipeContainer.isRefreshing = loadState.source.refresh is LoadState.Loading
            retryBtn.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    activity,
                    it.error.localizedMessage ?: it.error.message ?: it.error.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
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

        observe(viewModel.actionPermission) { success ->
            checkSavePermission(success = success, denied = {
                Toast.makeText(activity, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
            })
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

    private fun checkSavePermission(success: () -> Unit, denied: () -> Unit) {
        when {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> success()
            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> denied()
            else -> RequestPermissionLauncher(success, denied)?.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        permissionLaunchers.onEach(ActivityResultLauncher<String>::unregister)
    }

    private fun RequestPermissionLauncher(success: () -> Unit, denied: () -> Unit): ActivityResultLauncher<String>? {
        var launcher: ActivityResultLauncher<String>? = null
        launcher = activity?.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                success()
            } else {
                denied()
            }
            launcher?.unregister()
            permissionLaunchers.remove(launcher)
        }.apply {
            this?.let(permissionLaunchers::add)
        }
        return launcher
    }
}