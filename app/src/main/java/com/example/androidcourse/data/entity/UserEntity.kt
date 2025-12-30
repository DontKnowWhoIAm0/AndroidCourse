package com.example.androidcourse.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidcourse.utils.Сonstants

@Entity(tableName = Сonstants.TABLE_USERS)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String,
    val password: String,
    val salt: String,
    val isDeleted: Boolean = false,
    @ColumnInfo(defaultValue = Сonstants.DELETE_DATE_NULL) val deleteDate: Long? = null
)