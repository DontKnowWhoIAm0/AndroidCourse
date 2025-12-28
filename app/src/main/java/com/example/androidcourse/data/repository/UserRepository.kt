package com.example.androidcourse.data.repository

import com.example.androidcourse.data.dao.UserDao
import com.example.androidcourse.data.entity.UserEntity
import com.example.androidcourse.utils.PasswordHasher
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val userDao: UserDao
) {

    suspend fun registration(email: String, password: String, salt: String): Result<Unit> {
        if (userDao.isEmailExists(email) > 0) {
            return Result.failure(Exception("Аккаунт с таким email уже существует!"))
        }

        val user = UserEntity(
            email = email,
            password = password,
            salt = salt,
            isDeleted = false,
            deleteDate = null
        )

        userDao.insert(user)
        return Result.success(Unit)
    }

    suspend fun login(email: String, password: String): Result<Boolean> {
        return try {
            val userEntity = userDao.getUserByEmail(email)
            if (userEntity != null) {
                val salt = userEntity.salt
                val hashedInputPassword = PasswordHasher.hash(password, salt)
                if (hashedInputPassword == userEntity.password) {
                    Result.success(true)
                } else {
                    Result.failure(Exception("Неверный пароль"))
                }
            } else {
                Result.failure(Exception("Пользователь не найден"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getUserByEmail(email)
    }

    fun observeUsers(): Flow<List<UserEntity>> {
        return userDao.observeUsers()
    }
}