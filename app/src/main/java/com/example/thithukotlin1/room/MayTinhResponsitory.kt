package com.example.thithukotlin1.room

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class MayTinhResponsitory(private val mayTinhDao: MayTinhDao) {
    val getAll: Flow<List<MayTinh>> = mayTinhDao.getAll()

    @WorkerThread
    suspend fun insert(mayTinh: MayTinh) {
        mayTinhDao.insert(mayTinh)
    }

    @WorkerThread
    suspend fun update(mayTinh: MayTinh) {
        mayTinhDao.update(mayTinh)
    }

    @WorkerThread
    suspend fun delete(mayTinh: MayTinh) {
        mayTinhDao.delete(mayTinh)
    }


}