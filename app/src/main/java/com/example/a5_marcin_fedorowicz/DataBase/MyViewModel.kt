package com.example.a5_marcin_fedorowicz.DataBase

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.Flow


class MyViewModel(context: Context): ViewModel() {
    private var myRepository: MyRepository = MyRepository.getInstance(context)
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T: ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T{
                val application = checkNotNull(extras[APPLICATION_KEY])
                return MyViewModel(application.applicationContext) as T
            }
        }
    }
    fun getAllItems(): Flow<List<DBItem>>? = myRepository.getData()
    fun insertItem(item: DBItem) = myRepository.addItem(item)
    fun deleteItem(item: DBItem) = myRepository.deleteItem(item)
    fun modifyItem(id: Int, productName: String, use: String, amount: Int, productType: Int, available: Boolean, bought: Boolean) = myRepository.modifyItem(id, productName, use, amount, productType, available, bought)
}