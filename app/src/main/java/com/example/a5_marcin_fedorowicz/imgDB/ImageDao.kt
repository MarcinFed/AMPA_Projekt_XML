package com.example.a5_marcin_fedorowicz.imgDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ImageDao {
    @Query("SELECT * FROM image_table ORDER BY id ASC")
    fun getAllData(): MutableList<ImageDBItem>?
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: ImageDBItem): Long
    @Delete
    fun delete(item: ImageDBItem): Int
}