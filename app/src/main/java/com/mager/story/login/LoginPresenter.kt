package com.mager.story.login

import android.support.annotation.ArrayRes
import com.mager.story.R
import com.mager.story.StoryApplication
import com.mager.story.constant.EnumConstant.FolderType
import com.mager.story.core.CorePresenter
import com.mager.story.data.MenuData
import com.mager.story.util.EncryptionUtil
import com.mager.story.util.FileUtil
import com.mager.story.util.ResourceUtil
import java.util.*

/**
 * Created by Gerry on 23/10/2016.
 */

internal class LoginPresenter(viewModel: LoginViewModel) : CorePresenter<LoginViewModel>(viewModel) {

    private val provider: LoginProvider

    init {
        provider = LoginProvider()
    }

    fun loadEmail() {
        viewModel.setEmail(provider.loadEmail())
    }

    fun setLoading(loading: Boolean) {
        viewModel.isLoading = loading
    }

    fun setMenuDataModel(dataModel: MenuData) {
        viewModel.setMenuData(dataModel)
    }

    fun saveMenuDataToDevice() {
        provider.saveMenuData(viewModel.getMenuData())
    }

    fun incrementAriesCount() {
        viewModel.setAriesCount(viewModel.getAriesCount() + 1)
    }

    fun saveEmailToDevice() {
        provider.saveEmail(viewModel.getEmail())
    }

    fun clearOutdatedData() {
        val removeList = ArrayList<String>()

        removeFolderContentIfOutdated(removeList, FolderType.MENU)
        removeFolderContentIfOutdated(removeList, FolderType.PHOTO)
        removeFolderContentIfOutdated(removeList, FolderType.STORY)
        removeFolderContentIfOutdated(removeList, FolderType.AUDIO)
        removeFolderContentIfOutdated(removeList, FolderType.VIDEO)

        showRemovalToast(removeList)
    }

    private fun showRemovalToast(removeList: List<String>) {
        val builder = StringBuilder(ResourceUtil.getString(R.string.login_folder_removed))

        for (i in removeList.indices) {
            val item = removeList[i]
            builder.append(item)

            if (i != removeList.size - 1) {
                builder.append(", ")
            }
        }

        if (!removeList.isEmpty()) {
            ResourceUtil.showToast(builder.toString())
        }
    }

    private fun removeFolderContentIfOutdated(removeList: MutableList<String>, @FolderType folderType: String) {
        val localData = provider.localData
        val currentData = viewModel.getMenuData()

        if (!provider.isLocalDataValid(localData, currentData, folderType)) {
            FileUtil.removeFolderContent(folderType)

            removeList.add(folderType)
        }
    }

    val isValidOnline: Boolean
        get() = viewModel.getAriesCount() == StoryApplication.ARIES_COUNT

    val isValidOffline: Boolean
        get() = isOfflineAriesCount && isValidEmail && isValidPassword

    private val isValidEmail: Boolean
        get() = isValidKey(R.array.valid_email, viewModel.getEmail())

    private val isValidPassword: Boolean
        get() = isValidKey(R.array.valid_password, viewModel.getPassword())

    private fun isValidKey(@ArrayRes arrayRes: Int, key: String): Boolean {
        for (password in ResourceUtil.getStringArray(arrayRes)) {
            val encryptedValue = EncryptionUtil.encrypt(key)
            if (encryptedValue != null && encryptedValue.equals(password, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    private val isOfflineAriesCount: Boolean
        get() = viewModel.getAriesCount() == StoryApplication.ARIES_COUNT_OFFLINE

    fun doesMenuDataExist(): Boolean {
        return provider.localData != null
    }

    fun setLocalMenuData() {
        viewModel.setMenuData(provider.localData)
    }

    fun incrementWrongCount() {
        viewModel.wrongCount.set(viewModel.wrongCount.get() + 1)
    }
}
