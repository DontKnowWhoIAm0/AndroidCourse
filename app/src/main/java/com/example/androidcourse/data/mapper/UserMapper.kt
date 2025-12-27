package com.example.androidcourse.data.mapper

import com.example.androidcourse.data.entity.UserEntity
import com.example.androidcourse.model.User
import java.util.Date

fun UserEntity.toModel(): User = User(
    id = id,
    email = email,
    password = password,
    isDeleted = isDeleted,
    deleteDate = deleteDate?.let { Date(it) }
)

fun User.toEntity(salt: String): UserEntity = UserEntity(
    id = id,
    email = email,
    password = password,
    salt = salt,
    isDeleted = isDeleted,
    deleteDate = deleteDate?.time
)