package com.example.ucp2pam_101.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2pam_101.data.dao.DosenDao
import com.example.ucp2pam_101.data.dao.MatakuliahDao
import com.example.ucp2pam_101.data.entity.Dosen
import com.example.ucp2pam_101.data.entity.Matakuliah

@Database(entities = [Dosen::class, Matakuliah::class],version = 2, exportSchema = false)
abstract class KrsDatabase: RoomDatabase() {

    abstract fun dosenDao(): DosenDao
    abstract fun matakuliahDao(): MatakuliahDao

    companion object {
        @Volatile
        private var Instance: KrsDatabase? = null

        fun getDatabase(context: Context): KrsDatabase {
            return (Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context, KrsDatabase::class.java,
                    "KrsDatabase"
                )
                    .build().also { Instance = it }
            }


                    )
        }
    }

}