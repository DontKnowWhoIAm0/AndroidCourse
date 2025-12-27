package com.example.androidcourse.data.mapper

import com.example.androidcourse.data.entity.UserEntity
import com.example.androidcourse.model.User

fun UserEntity.toModel(): User = User(
    id = id,
    email = email,
    password = password,
    isDeleted = isDeleted,
    deleteDate = deleteDate
)

fun User.toEntity(): UserEntity = UserEntity(
    id = id,
    email = email,
    password = password,
    isDeleted = isDeleted,
    deleteDate = deleteDate
)