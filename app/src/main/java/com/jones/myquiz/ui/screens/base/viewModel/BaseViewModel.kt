package com.jones.myquiz.ui.screens.base.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext
import java.lang.Exception

abstract class BaseViewModel : ViewModel() {
    protected val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error
    protected val _success = MutableSharedFlow<String>()
    val success: SharedFlow<String> = _success

    open fun onCreate() {

    }

    suspend fun <T> errorHandler(callback: suspend () -> T?): T? {
        return try {
            callback()
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                _error.emit(e.message.toString())
            }
            e.printStackTrace()
            null
        }
    }


}