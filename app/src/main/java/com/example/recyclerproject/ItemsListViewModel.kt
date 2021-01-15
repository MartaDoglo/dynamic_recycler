package com.example.recyclerproject

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclerproject.Data.DataSource
import com.example.recyclerproject.Data.Item
import java.lang.IllegalArgumentException

class ItemsListViewModel (val dataSource: DataSource): ViewModel() {
    val itemLiveData = dataSource.getItemList()

    fun insertItem(){
        val newItem = Item(
            getItemId(),
            "TEMP"
        )
        dataSource.addItem(newItem)
    }

    private fun getItemId(): Long{
        return dataSource.getFreeId()
    }

    fun showItemIndex(item: Item): Int? {
        return dataSource.getItemIndex(item)
    }

    fun showList(): LiveData<List<Item>> {
        return dataSource.getItemList()
    }

    fun removeItem(item: Item) {
        dataSource.removeItem(item)
    }

    fun getItemForId(id: Long) : Item? {
        return dataSource.getItemForId(id)
    }
}

class ItemsListViewModelFactory(private val context: Context): ViewModelProvider.Factory{

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemsListViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ItemsListViewModel(
                dataSource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}