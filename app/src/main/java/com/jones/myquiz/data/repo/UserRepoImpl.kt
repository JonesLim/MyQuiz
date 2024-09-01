package com.jones.myquiz.data.repo

import android.util.Log
import com.jones.myquiz.core.service.AuthService
import com.jones.myquiz.data.model.User
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepoImpl(
    private val authService: AuthService,
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : UserRepo {

    private fun getDbRef(): CollectionReference {
        return db.collection("users")
    }

    private fun getUid(): String {
        val firebaseUser = authService.getCurrentUser()
        return firebaseUser?.uid ?: throw Exception("No authorized user found")
    }

    override suspend fun addUser(user: User) {
        getDbRef().document(getUid()).set(user.toHashMap()).await()
    }

    override suspend fun getUser(uid: String): User? {
        val doc = getDbRef().document(uid).get().await()
        return doc.data?.let {
            it["id"] = uid
            User.fromHashMap(it)
        }
    }

    override suspend fun removeUser() {
        getDbRef().document(getUid()).delete().await()
    }

    override suspend fun updateUser(user: User) {
        getDbRef().document(getUid()).set(user.toHashMap()).await()
    }

    override suspend fun getUserRole(uid: String): String? {
        return try {
            val doc = getDbRef().document(uid).get().await()
            doc.data?.get("role") as? String
        } catch (e: Exception) {
            Log.e("UserRepo", "Error fetching user role", e)
            null // Return null if there's an error
        }
    }


}
