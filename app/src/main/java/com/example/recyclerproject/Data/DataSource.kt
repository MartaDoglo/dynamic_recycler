package com.example.recyclerproject.Data

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DataSource (resources: Resources){
    private val initialItemList  = itemList(resources)
    private val itemLiveData = MutableLiveData(initialItemList)


    fun addItem(item: Item){

        val currentList = itemLiveData.value
        if (currentList == null){
            itemLiveData.postValue(listOf(item))
        } else {
            val updatedList = currentList.toMutableList()

            updatedList.add(5, item)
            itemLiveData.postValue(updatedList)
        }
    }

    fun removeItem(item: Item){
        val currentList = itemLiveData.value
        if (currentList != null){
            val updatedList = currentList.toMutableList()
            updatedList.remove(item)
            itemLiveData.postValue(updatedList)
        }
    }

    fun getItemList(): LiveData<List<Item>>{
        return itemLiveData
    }

    fun getItemForId(id: Long): Item? {
        itemLiveData.value?.let { items ->
            return items.firstOrNull{ it.id == id}
        }
        return null
    }

    companion object {
        private var INSTANCE: DataSource? = null
        fun getDataSource(resources: Resources): DataSource{
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}