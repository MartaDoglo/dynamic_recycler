package com.example.recyclerproject

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclerproject.Data.DataSource
import com.example.recyclerproject.Data.Item
import java.lang.IllegalArgumentException

class ItemsListViewModel (val dataSource: DataSource): ViewModel() {
    val itemLiveData = dataSource.getItemList()

    fun insertItem(){
        val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val newItem = Item(
                getItemId(),
                List(6) { alphabet.random() }.joinToString("")
        )
        dataSource.addItem(newItem)
    }

    private fun getItemId(): Long{
        return dataSource.getFreeId()
    }

    fun removeItem(item: Item) {
        dataSource.removeItem(item)
    }

}

class ItemsListViewModelFactory(private val context: Context): ViewModelProvider.Factory{

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemsListViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ItemsListViewModel(
                dataSource = DataSource.getDataSource()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}