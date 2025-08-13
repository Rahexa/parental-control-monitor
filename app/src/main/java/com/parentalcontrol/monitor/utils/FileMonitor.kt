package com.parentalcontrol.monitor.utils

import android.content.Context
import android.os.Environment
import android.webkit.MimeTypeMap
import com.parentalcontrol.monitor.models.FileAccessData
import java.io.File
import java.util.*

class FileMonitor(private val context: Context) {
    
    private val monitoredDirectories = listOf(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
    )
    
    private val lastScanTime = mutableMapOf<String, Long>()
    
    fun getRecentFileAccess(): List<FileAccessData> {
        val recentFiles = mutableListOf<FileAccessData>()
        val currentTime = System.currentTimeMillis()
        val scanThreshold = 30 * 60 * 1000L // 30 minutes
        
        monitoredDirectories.forEach { directory ->
            if (directory.exists() && directory.canRead()) {
                scanDirectory(directory, currentTime - scanThreshold, recentFiles)
            }
        }
        
        return recentFiles.sortedByDescending { it.accessTime }
    }
    
    private fun scanDirectory(directory: File, threshold: Long, result: MutableList<FileAccessData>) {
        try {
            directory.listFiles()?.forEach { file ->
                if (file.isFile && file.lastModified() > threshold) {
                    val fileData = createFileAccessData(file)
                    if (fileData != null) {
                        result.add(fileData)
                    }
                } else if (file.isDirectory) {
                    scanDirectory(file, threshold, result)
                }
            }
        } catch (e: SecurityException) {
            // Permission denied for this directory
        }
    }
    
    private fun createFileAccessData(file: File): FileAccessData? {
        return try {
            val fileName = file.name
            val filePath = file.absolutePath
            val fileSize = file.length()
            val fileType = getFileType(fileName)
            val accessTime = file.lastModified()
            
            // Only monitor specific file types that might be of interest
            if (isMonitoredFileType(fileType)) {
                FileAccessData(
                    fileName = fileName,
                    filePath = filePath,
                    fileSize = fileSize,
                    fileType = fileType,
                    accessTime = accessTime,
                    accessType = "modified"
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
    
    private fun getFileType(fileName: String): String {
        val extension = fileName.substringAfterLast('.', "")
        return when (extension.lowercase()) {
            "jpg", "jpeg", "png", "gif", "bmp", "webp" -> "Image"
            "mp4", "avi", "mkv", "mov", "wmv", "flv", "webm" -> "Video"
            "mp3", "wav", "flac", "aac", "ogg", "m4a" -> "Audio"
            "pdf" -> "Document"
            "doc", "docx", "txt", "rtf" -> "Text Document"
            "zip", "rar", "7z", "tar", "gz" -> "Archive"
            "apk" -> "Android App"
            else -> extension.uppercase().ifEmpty { "Unknown" }
        }
    }
    
    private fun isMonitoredFileType(fileType: String): Boolean {
        return fileType in listOf(
            "Image", "Video", "Audio", "Document", 
            "Text Document", "Archive", "Android App"
        )
    }
    
    fun startRealTimeMonitoring() {
        // TODO: Implement FileObserver for real-time monitoring
        // This would require more complex implementation with FileObserver
    }
}
