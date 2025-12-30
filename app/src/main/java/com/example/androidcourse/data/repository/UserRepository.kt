package com.example.androidcourse.data.repository

import com.example.androidcourse.data.dao.UserDao
import com.example.androidcourse.data.entity.UserEntity
import com.example.androidcourse.utils.PasswordHasher
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val userDao: UserDao
) {

    suspend fun registration(email: String, password: String, salt: String) {
        val user = UserEntity(
            email = email,
            password = password,
            salt = salt,
            isDeleted = false,
            deleteDate = null
        )
        userDao.insert(user)
    }

    suspend fun login(email: String, password: String): UserEntity? {
        val userEntity = userDao.getUserByEmail(email) ?: return null
        val hashedInputPassword = PasswordHasher.hash(password, userEntity.salt)
        if (userEntity.password != hashedInputPassword) return null
        return userEntity
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getUserByEmail(email)
    }

    suspend fun getUserById(id: Int): UserEntity? {
        return userDao.getUserById(id)
    }

    suspend fun isEmailExists(email: String): Boolean {
        return userDao.isEmailExists(email) > 0
    }

    fun observeUsers(): Flow<List<UserEntity>> {
        return userDao.observeUsers()
    }

    suspend fun softDeleteUser(userId: Int) {
        userDao.softDelete(userId = userId, deleteDate = System.currentTimeMillis())
    }

    suspend fun restoreUser(userId: Int) {
        userDao.update(userDao.getUserById(userId)!!.copy(isDeleted = false, deleteDate = null))
    }

    suspend fun deleteOldDeletedUsers() {
        val sevenDaysMillis = 7 * 24 * 60 * 60 * 1000L
        val time = System.currentTimeMillis() - sevenDaysMillis
        userDao.deleteOldDeletedUsers(time)
    }

    suspend fun delete(userId: Int) {
        userDao.getUserById(userId)?.let { userDao.delete(it) }
    }
}