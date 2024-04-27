package com.example.a5_marcin_fedorowicz.imgDB

import android.content.Context


class ImageRepository(context: Context) {
    private var dataList: MutableList<ImageDBItem>? = null
    private var imgDao: ImageDao
    private var db: ImageDB

    companion object{
        private var R_INSTANCE: ImageRepository? = null
        fun getInstance(context: Context): ImageRepository{
            if(R_INSTANCE == null){
                R_INSTANCE = ImageRepository(context)
            }
            return R_INSTANCE as ImageRepository
        }
    }

    init {
        db = ImageDB.getDatabase(context)!!
        imgDao = db.imageDao()!!
    }

    fun getData(): MutableList<ImageDBItem>?{
        dataList = imgDao.getAllData()
        return dataList
    }

    fun addItem(item: ImageDBItem): Boolean{
        return imgDao.insert(item) >= 0
    }

    fun delItem(itemPosition: Int) {
        imgDao.delete(dataList!!.get(itemPosition))
    }

}