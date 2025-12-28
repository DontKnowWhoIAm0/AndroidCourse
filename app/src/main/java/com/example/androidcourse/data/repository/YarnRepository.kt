package com.example.androidcourse.data.repository

import com.example.androidcourse.data.dao.YarnDao
import com.example.androidcourse.data.mapper.toEntity
import com.example.androidcourse.data.mapper.toModel
import com.example.androidcourse.model.Yarn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class YarnRepository(
    private val yarnDao: YarnDao
) {

    suspend fun addYarn(yarn: Yarn) {
        yarnDao.insert(yarn.toEntity())
    }

    suspend fun updateYarn(yarn: Yarn) {
        yarnDao.update(yarn.toEntity())
    }

    suspend fun deleteYarn(yarn: Yarn) {
        yarnDao.delete(yarn.toEntity())
    }

    fun getAllYarns(): Flow<List<Yarn>> =
        yarnDao.getAll().map { list ->
            list.map { it.toModel() }
        }

    suspend fun getYarnById(id: Int): Yarn? = yarnDao.getYarnById(id)?.toModel()

    fun getAllYarnsSortedByBrand(): Flow<List<Yarn>> =
        yarnDao.sortByBrand().map { list ->
            list.map { it.toModel() }
        }

    fun getAllYarnsSortedByBrandDesc(): Flow<List<Yarn>> =
        yarnDao.sortByBrandDesc().map { list ->
            list.map { it.toModel() }
        }

    fun getAllYarnsSortedByWeight(): Flow<List<Yarn>> =
        yarnDao.sortByWeight().map { list ->
            list.map { it.toModel() }
        }

    fun getAllYarnsSortedByWeightDesc(): Flow<List<Yarn>> =
        yarnDao.sortByWeightDesc().map { list ->
            list.map { it.toModel() }
        }

    fun getAllYarnsSortedBySkeinLength(): Flow<List<Yarn>> =
        yarnDao.sortBySkeinLength().map { list ->
            list.map { it.toModel() }
        }

    fun getAllYarnsSortedBySkeinLengthDesc(): Flow<List<Yarn>> =
        yarnDao.sortBySkeinLengthDesc().map { list ->
            list.map { it.toModel() }
        }
}