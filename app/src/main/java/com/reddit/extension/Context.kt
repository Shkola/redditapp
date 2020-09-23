package com.reddit.extension

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

suspend fun Context.saveImage(
    bitmap: Bitmap,
    fileName: String
) = withContext(Dispatchers.IO) {
    if (android.os.Build.VERSION.SDK_INT >= 29) {
        val values = contentValues()

        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures")
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        values.put(MediaStore.Images.Media.IS_PENDING, true)
        // RELATIVE_PATH and IS_PENDING are introduced in API 29.

        val uri: Uri? = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        if (uri != null) {
            saveImageToStream(bitmap, contentResolver.openOutputStream(uri))
            values.put(MediaStore.Images.Media.IS_PENDING, false)
            contentResolver.update(uri, values, null, null)
        }
    } else {
        val directory = File(Environment.getExternalStorageDirectory().toString())
        // getExternalStorageDirectory is deprecated in API 29

        if (!directory.exists()) {
            directory.mkdirs()
        }
        val fileName = "$fileName.png"
        val file = File(directory, fileName)
        saveImageToStream(bitmap, FileOutputStream(file))
        if (file.absolutePath != null) {
            val values = contentValues()
            values.put(MediaStore.Images.Media.DATA, file.absolutePath)
            // .DATA is deprecated in API 29
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        }
    }
}

private fun contentValues(): ContentValues {
    val values = ContentValues()
    values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
    values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
    values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
    return values
}

private fun saveImageToStream(
    bitmap: Bitmap,
    outputStream: OutputStream?,
) {
    outputStream.use { outputStream ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    }
}