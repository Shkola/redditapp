package com.reddit.client.top

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.asLiveData
import androidx.paging.cachedIn
import androidx.paging.map
import com.reddit.R
import com.reddit.base.BaseViewModel
import com.reddit.sdk.ModuleApi
import com.reddit.sdk.timeDelta
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit

class TopViewModel @ViewModelInject constructor(
    @ApplicationContext context: Context,
    moduleApi: ModuleApi
) : BaseViewModel() {

    private val resources = context.resources

    val pagingTopListLiveData = moduleApi.getTop()
        .map { dataSource ->
            dataSource.map { item ->
                val hoursDelta = TimeUnit.MILLISECONDS.toHours(item.timeDelta).toInt()
                item.toTopRedditUi(
                    description = resources.getQuantityString(R.plurals.descriptionPlaceholder, hoursDelta, item.author, hoursDelta),
                    commentsCount = resources.getQuantityString(R.plurals.commentsPlaceholder, item.commentsNumber, item.commentsNumber),
                    onSaveImage = { onSaveImageToGallery(item.thumbnail) }
                )
            }
        }
        .cachedIn(coroutineScope)
        .asLiveData(coroutineScope.coroutineContext)

    private fun onSaveImageToGallery(imageUrl: String) {
        TODO("Not yet implemented")
    }
}
