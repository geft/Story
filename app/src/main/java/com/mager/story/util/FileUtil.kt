package com.mager.story.util

import com.mager.story.R
import com.mager.story.StoryApplication
import com.mager.story.constant.EnumConstant
import com.mager.story.constant.EnumConstant.FolderType
import com.mager.story.constant.EnumConstant.Tag
import com.mager.story.data.DownloadInfo

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.RandomAccessFile

/**
 * Created by Gerry on 31/10/2016.
 */

object FileUtil {

    fun saveBytesToDevice(bytes: ByteArray, code: String, downloadInfo: DownloadInfo, isMedia: Boolean) {
        val file = getFileFromCode(code, downloadInfo)

        if (file.exists()) file.delete()

        if (downloadInfo.group != null) {
            file.parentFile.mkdirs()
        }

        try {
            FileOutputStream(file.path).use { stream ->
                if (isMedia) {
                    stream.write(EncryptionUtil.flipFirst100(bytes))
                } else {
                    stream.write(EncryptionUtil.flip(bytes))
                }
                stream.close()
                LogUtil.logInfo(downloadInfo.downloadType, ResourceUtil.getString(R.string.file_save_success, file.path))
            }
        } catch (e: Exception) {
            LogUtil.logError(downloadInfo.downloadType, ResourceUtil.getString(R.string.file_save_error, file.path), e)
        }

    }

    fun readBytesFromDevice(file: File, isMedia: Boolean): ByteArray? {
        if (file.exists()) {
            try {
                val bytes: ByteArray

                if (isMedia) {
                    bytes = EncryptionUtil.flipFirst100(readFile(file))
                } else {
                    bytes = EncryptionUtil.flip(readFile(file))
                }
                LogUtil.logInfo(Tag.FILE, ResourceUtil.getString(R.string.file_load_success, file.path))
                return bytes
            } catch (e: Exception) {
                LogUtil.logError(Tag.FILE, ResourceUtil.getString(R.string.file_load_error, file.path), e)
            }

        }

        return null
    }

    @Throws(IOException::class)
    private fun readFile(file: File): ByteArray {
        // Open file
        RandomAccessFile(file, "r").use { f ->
            // Get and check length
            val longLength = f.length()
            val length = longLength.toInt()
            if (length.toLong() != longLength)
                throw IOException("File size >= 2 GB")
            // Read file and return data
            val data = ByteArray(length)
            f.readFully(data)
            return data
        }
    }

    private fun getFileFromFileName(downloadInfo: DownloadInfo, fileName: String): File {
        val file: File
        if (downloadInfo.group != null) {
            file = File(getSubFolder(downloadInfo.folderType).toString() + File.separator + downloadInfo.group, fileName)
        } else {
            file = File(getSubFolder(downloadInfo.folderType), fileName)
        }

        return file
    }

    fun getFileFromCode(code: String, downloadInfo: DownloadInfo): File {
        return getFileFromFileName(downloadInfo, getFileName(code, downloadInfo))
    }

    fun removeFolderContent(@FolderType folderType: String) {
        deleteRecursive(getSubFolder(folderType))
    }

    fun createFileInFolder(@FolderType folderType: String, name: String): File {
        val subDir = getSubFolder(folderType)
        subDir.mkdirs()

        return File(subDir, name)
    }

    private val filesDir: File
        get() = StoryApplication.getInstance().filesDir

    private fun getSubFolder(@FolderType folderType: String): File {
        return File(filesDir.toString() + File.separator + folderType)
    }

    fun getFileName(code: String, downloadInfo: DownloadInfo): String {
        var prefix = ""

        when (downloadInfo.downloadType) {
            EnumConstant.DownloadType.MENU_PHOTO -> prefix = EnumConstant.FilePrefix.MENU_PHOTO
            EnumConstant.DownloadType.MENU_STORY -> prefix = EnumConstant.FilePrefix.MENU_STORY
            EnumConstant.DownloadType.PHOTO_THUMB -> prefix = EnumConstant.FilePrefix.PHOTO_THUMB
            EnumConstant.DownloadType.PHOTO_FULL -> prefix = EnumConstant.FilePrefix.PHOTO_FULL
        }

        return prefix + code + downloadInfo.fileExtension
    }

    fun clearInternalData() {
        deleteRecursive(StoryApplication.getInstance().filesDir)
    }

    fun clearCache() {
        deleteRecursive(StoryApplication.getInstance().cacheDir)
    }

    private fun deleteRecursive(fileOrDirectory: File) {

        if (fileOrDirectory.isDirectory) {
            for (child in fileOrDirectory.listFiles()) {
                deleteRecursive(child)
            }
        }

        fileOrDirectory.delete()
    }
}