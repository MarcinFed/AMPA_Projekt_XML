package com.example.a5_marcin_fedorowicz.imgDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_table")
class ImageDBItem {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @ColumnInfo(name = "path")
    var path: String? = null

    constructor()
    constructor(imgPath: String){
        path = imgPath
    }
}