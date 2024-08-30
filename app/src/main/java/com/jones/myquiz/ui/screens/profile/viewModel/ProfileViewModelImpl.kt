package com.jones.myquiz.ui.screens.profile.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jones.myquiz.core.service.AuthService
import com.jones.myquiz.core.service.StorageService
import com.jones.myquiz.data.model.User
import com.jones.myquiz.data.repo.UserRepo
import com.jones.myquiz.ui.screens.base.viewModel.BaseViewModel
import com.jones.myquiz.ui.screens.profile.viewModel.ProfileViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModelImpl @Inject constructor(
    private val authService: AuthService,
    private val userRepo: UserRepo,
    private val storageService: StorageService
): BaseViewModel(), ProfileViewModel {

    private val _user = MutableStateFlow(User(name = "Unknown", email = "Unknown", role = "Unknown"))
    val user: StateFlow<User> = _user

    private val _profileUri = MutableStateFlow<Uri?>(null)
    val profileUri: StateFlow<Uri?> = _profileUri

    private val _finish = MutableSharedFlow<Unit>()
    val finish: SharedFlow<Unit> = _finish


    init {
        getCurrentUser()
        getProfilePicUri()
    }


    override fun logout() {
        authService.logout()
        viewModelScope.launch {
            _finish.emit(Unit)
        }
    }


    override fun getCurrentUser() {
        val firebaseUser = authService.getCurrentUser()
        Log.d("debugging", firebaseUser?.uid.toString())
        firebaseUser?.let {
            viewModelScope.launch(Dispatchers.IO) {
                errorHandler { userRepo.getUser(it.uid) }?.let {  user ->
                    Log.d("debugging", user.toString())
                    _user.value = user
                }
            }
        }
    }

    fun updateProfilePic(uri: Uri) {
        user.value.id?.let { userId ->
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val name = "$userId.jpg"
                    storageService.addImage(name, uri)
                    _profileUri.value = storageService.getImage(name) // Update profile URI directly after upload
                } catch (e: Exception) {
                    Log.e("ProfileViewModel", "Error updating profile pic: ${e.message}")
                }
            }
        }
    }


    fun getProfilePicUri() {
        viewModelScope.launch(Dispatchers.IO) {
            authService.getCurrentUser()?.uid?.let {
                val uri = storageService.getImage("$it.jpg")
                withContext(Dispatchers.Main) {
                    _profileUri.value = uri
                }
            }
        }
    }

}