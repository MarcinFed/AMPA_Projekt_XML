package com.example.a5_marcin_fedorowicz.Repo

class DataRepo {

    private var dataList: MutableList<DataItem> = mutableListOf()

    companion object{
        private var INSTANCE: DataRepo? = null
        fun getInstance(): DataRepo {
            if(INSTANCE == null){
                INSTANCE = DataRepo()
            }
            return INSTANCE as DataRepo
        }
    }

    fun getData() : MutableList<DataItem> {
        return dataList
    }

    fun deleteItem(position: Int): Boolean {
        dataList.removeAt(position)
        return true
    }

    fun addItem(item: DataItem): Boolean {
        dataList.add(item)
        return true
    }

    fun modifyItem(position: Int, productName: String, use: String, amount: Int, productType: Int, available: Boolean, bought: Boolean): Boolean {
        var item = dataList.removeAt(position)
        item.Product = productName
        item.Use = use
        item.Amount = amount
        item.ProductType = productType
        item.Available = available
        item.Bought = bought
        dataList.add(position, item)
        return true
    }


}