package com.example.ucp2pam_101.repository

import com.example.ucp2pam_101.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

interface RepositoryDosen {
    suspend fun insertDosen(dosen: Dosen)

    //getAllDosen
    fun getAllDosen(): Flow<List<Dosen>>

    //getDosen
    fun getDosen(nidn: String): Flow<Dosen>
}