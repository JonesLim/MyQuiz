package com.jones.myquiz.data.repo

import com.jones.myquiz.data.model.User

interface UserRepo {
    suspend fun addUser(user: User)
    suspend fun getUser(uid: String): User?
    suspend fun removeUser()
    suspend fun updateUser(user: User)
    suspend fun getUserRole(uid: String): String?
}