package com.family.safety.helper.utils

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import com.family.safety.helper.database.MediaFileEntity
import com.family.safety.helper.database.MonitoringDatabase
import com.family.safety.helper.services.TelegramService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class MediaObserver(
    private val context: Context,
    private val database: MonitoringDatabase,
    private val telegramService: TelegramService
) : ContentObserver(Handler(Looper.getMainLooper())) {
    
    private val scope = CoroutineScope(Dispatchers.IO)
    
    fun startObserving() {
        // Observe images
        context.contentResolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            true,
            this
        )
        
        // Observe videos
        context.contentResolver.registerContentObserver(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            true,
            this
        )
        
        // Observe audio
        context.contentResolver.registerContentObserver(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            true,
            this
        )
    }
    
    fun stopObserving() {
        context.contentResolver.unregisterContentObserver(this)
    }
    
    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)
        
        uri?.let { mediaUri ->
            scope.launch {
                try {
                    val mediaInfo = getMediaInfo(mediaUri)
                    mediaInfo?.let { info ->
                        val mediaEntity = MediaFileEntity(
                            fileName = info.fileName,
                            filePath = info.filePath,
                            fileSize = info.fileSize,
                            fileType = info.fileType,
                            dateAdded = info.dateAdded,
                            timestamp = System.currentTimeMillis()
                        )
                        
                        database.mediaFileDao().insertMediaFile(mediaEntity)
                        
                        // Try to send immediately
                        val success = telegramService.sendMediaFileAlert(
                            info.fileName,
                            info.filePath,
                            info.fileType,
                            info.fileSize,
                            info.dateAdded
                        )
                        
                        if (success) {
                            database.mediaFileDao().markAsSent(mediaEntity.id)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
    
    private fun getMediaInfo(uri: Uri): MediaInfo? {
        return try {
            val projection = arrayOf(
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.DATA,
                MediaStore.MediaColumns.SIZE,
                MediaStore.MediaColumns.DATE_ADDED
            )
            
            context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val fileName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME))
                    val filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA))
                    val fileSize = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.SIZE))
                    val dateAdded = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED)) * 1000 // Convert to milliseconds
                    
                    val fileType = when {
                        uri.toString().contains("images") -> "Image"
                        uri.toString().contains("video") -> "Video"
                        uri.toString().contains("audio") -> "Audio"
                        else -> getFileTypeFromExtension(fileName)
                    }
                    
                    MediaInfo(fileName, filePath, fileSize, fileType, dateAdded)
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    private fun getFileTypeFromExtension(fileName: String): String {
        val extension = fileName.substringAfterLast('.', "").lowercase()
        return when (extension) {
            "jpg", "jpeg", "png", "gif", "bmp", "webp" -> "Image"
            "mp4", "avi", "mkv", "mov", "wmv", "flv", "webm" -> "Video"
            "mp3", "wav", "flac", "aac", "ogg", "m4a" -> "Audio"
            else -> "Unknown"
        }
    }
    
    data class MediaInfo(
        val fileName: String,
        val filePath: String,
        val fileSize: Long,
        val fileType: String,
        val dateAdded: Long
    )
}
