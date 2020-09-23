package com.reddit.client.top

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.cachedIn
import androidx.paging.map
import com.reddit.R
import com.reddit.base.BaseViewModel
import com.reddit.base.SingleLiveEvent
import com.reddit.extension.saveImage
import com.reddit.sdk.ImageUrl
import com.reddit.sdk.ModuleApi
import com.reddit.sdk.timeDelta
import com.squareup.picasso.Picasso
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit


class TopViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    moduleApi: ModuleApi
) : BaseViewModel() {

    private val resources = context.resources

    private val _actionMessage = SingleLiveEvent<String>()
    val actionMessage: LiveData<String> = _actionMessage

    private val _actionOpenImage = SingleLiveEvent<ImageUrl>()
    val actionOpenImage: LiveData<ImageUrl> = _actionOpenImage

    val pagingTopListLiveData = moduleApi.getTop()
        .map { dataSource ->
            dataSource.map { item ->
                val hoursDelta = TimeUnit.MILLISECONDS.toHours(item.timeDelta).toInt()
                item.toTopRedditUi(
                    description = resources.getQuantityString(R.plurals.descriptionPlaceholder, hoursDelta, item.author, hoursDelta),
                    commentsCount = resources.getQuantityString(R.plurals.commentsPlaceholder, item.commentsNumber, item.commentsNumber),
                    onSaveImage = { onSaveImageToGallery(item.thumbnail) },
                    onOpenImage = { _actionOpenImage.value = item.thumbnail }
                )
            }
        }
        .cachedIn(coroutineScope)
        .asLiveData(coroutineScope.coroutineContext)

    private fun onSaveImageToGallery(imageUrl: String) {
        coroutineScope.launch {
            val uri = Uri.parse(imageUrl)

            val bitmap = withContext(Dispatchers.IO) {
                Picasso.get()
                    .load(uri)
                    .get()
            }

            val fileExt = MimeTypeMap.getFileExtensionFromUrl(imageUrl)

            context.saveImage(bitmap, uri.lastPathSegment?.removeSuffix(".$fileExt")!!)
        }.invokeOnCompletion { error ->
            if (error != null) {
                _actionMessage.value = error.localizedMessage ?: error.message
                    ?: resources.getString(R.string.file_not_saved)
            } else {
                _actionMessage.value = resources.getString(R.string.image_saved)
            }
        }
    }
}
