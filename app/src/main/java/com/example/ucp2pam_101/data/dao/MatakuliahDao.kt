package com.example.ucp2pam_101.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2pam_101.data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow

interface MatakuliahDao {
    @Insert
    suspend fun insertMatakuliah(
        matakuliah: Matakuliah
    )

    @Query("SELECT * FROM matakuliah ORDER BY nama ASC")
    fun getAllMatakuliah(): Flow<List<Matakuliah>>

    @Query("SELECT * FROM matakuliah WHERE kode = :kode")
    fun getMatakuliah(kode: String): Flow<Matakuliah>

    @Delete
    suspend fun deleteMatakuliah(matakuliah: Matakuliah)

    @Update
    suspend fun updateMatakuliah(mahasiswa: Matakuliah)
}