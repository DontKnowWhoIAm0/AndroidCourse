package com.example.androidcourse.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.androidcourse.data.entity.YarnEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface YarnDao {
    @Insert
    suspend fun insert(yarn: YarnEntity)

    @Update
    suspend fun update(yarn: YarnEntity)

    @Delete
    suspend fun delete(yarn: YarnEntity)

    @Query("SELECT * FROM yarns")
    fun getAll(): Flow<List<YarnEntity>>

    @Query("SELECT * FROM yarns WHERE id = :id LIMIT 1")
    suspend fun getYarnById(id: Int): YarnEntity?

    @Query("SELECT * FROM yarns ORDER BY brand")
    fun sortByBrand(): Flow<List<YarnEntity>>

    @Query("SELECT * FROM yarns ORDER BY brand DESC")
    fun sortByBrandDesc(): Flow<List<YarnEntity>>

    @Query("SELECT * FROM yarns ORDER BY weight")
    fun sortByWeight(): Flow<List<YarnEntity>>

    @Query("SELECT * FROM yarns ORDER BY weight DESC")
    fun sortByWeightDesc(): Flow<List<YarnEntity>>

    @Query("SELECT * FROM yarns ORDER BY skeinLength")
    fun sortBySkeinLength(): Flow<List<YarnEntity>>

    @Query("SELECT * FROM yarns ORDER BY skeinLength DESC")
    fun sortBySkeinLengthDesc(): Flow<List<YarnEntity>>
}