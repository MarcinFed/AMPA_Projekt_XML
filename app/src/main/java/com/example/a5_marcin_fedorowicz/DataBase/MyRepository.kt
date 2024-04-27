package com.example.a5_marcin_fedorowicz.DataBase

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class MyRepository(context: Context) {
    private var dataList: Flow<List<DBItem>>? = null
    private var myDao: MyDao? = null
    private var db: MyDB? = null

    companion object{
        private var R_INSTANCE: MyRepository? = null
        fun getInstance(context: Context): MyRepository {
            if (R_INSTANCE==null){
                R_INSTANCE = MyRepository(context)
            }
            return R_INSTANCE as MyRepository
        }
    }

    init {
        db = MyDB.getDatabase(context)
        myDao = db?.myDao()
    }

    fun getData(): Flow<List<DBItem>>? {
        dataList = myDao?.getAllData()
        return dataList
    }

    fun addItem(item: DBItem) : Boolean {
        return myDao?.insert(item)!! >= 0
    }

    fun deleteItem(item: DBItem) : Boolean {
        return myDao?.delete(item)!! > 0
    }

    fun modifyItem(id: Int, productName: String, use: String, amount: Int, productType: Int, available: Boolean, bought: Boolean) {
        val modifiedItem = DBItem(productName, use, amount, productType, available)
        modifiedItem.id = id
        modifiedItem.Bought = bought
        myDao?.update(modifiedItem)
    }
}