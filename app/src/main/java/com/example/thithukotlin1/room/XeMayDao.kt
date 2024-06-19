package com.example.thithukotlin1.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.thithukotlin1.room.MayTinh
import kotlinx.coroutines.flow.Flow

@Dao
interface MayTinhDao {
    @Query("SELECT * FROM MayTinh")
    fun getAll(): Flow<List<MayTinh>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mayTinh: MayTinh)

    @Update
    suspend fun update(mayTinh: MayTinh)

    @Delete
    suspend fun delete(mayTinh: MayTinh)

}