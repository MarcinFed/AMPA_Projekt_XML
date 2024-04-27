package com.example.a5_marcin_fedorowicz.imgDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase


@Database(entities = [ImageDBItem::class], version = 1)
abstract class ImageDB: RoomDatabase() {
    abstract fun imageDao(): ImageDao?

    companion object{
        private var DB_INSTANCE: ImageDB? = null

        @Synchronized
        open fun getDatabase(context: Context): ImageDB?{
            if(DB_INSTANCE == null){
                DB_INSTANCE = databaseBuilder(
                    context.applicationContext,
                    ImageDB::class.java,
                    "image_database"
                ).allowMainThreadQueries()
                    .build()
            }
            return DB_INSTANCE
        }
    }
}