package com.jones.myquiz.core.service

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await

class StorageService(
    private val storage:StorageReference = FirebaseStorage.getInstance().reference
) {
    suspend fun addImage(name:String,uri:Uri){
        storage.child(name).putFile(uri).await()
    }

    suspend fun getImage(name: String):Uri? {
        return try {
            storage.child(name).downloadUrl.await()
        } catch (e:Exception){
            e.printStackTrace()
            null
        }
    }
}