package com.example.recyclerproject.Data

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DataSource (resources: Resources){
    private val initialItemList  = itemList(resources)
    private val itemLiveData = MutableLiveData(initialItemList)

    private val freeId: MutableList<Long> = mutableListOf<Long>()

    private fun getRandomPositionFromPull (): Int {
        if (itemLiveData.value?.size != 0) {
            return (0..itemLiveData.value!!.size).random()
        }
        return 0
    }

    fun addItem(item: Item) {
        val position = getRandomPositionFromPull()
        val currentList = itemLiveData.value
        if (currentList == null){
            itemLiveData.postValue(listOf(item))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(position, item)
            itemLiveData.postValue(updatedList)
        }
    }

    fun removeItem(item: Item){
        freeId.add(item.id)
        val currentList = itemLiveData.value
        if (currentList != null){
            val updatedList = currentList.toMutableList()
            updatedList.remove(item)
            itemLiveData.postValue(updatedList)
        }
    }

    fun getFreeId(): Long {
        if (freeId.isNotEmpty()) {
            return freeId.removeAt(0)
        }
        if (itemLiveData.value?.size != 0){
            return (itemLiveData.value!!.size.toLong() + 1)
        }
        return 0
    }

    fun getConut(): Int? {
        return itemLiveData.value?.size
    }

    fun getItemIndex(item: Item): Int? {
        return itemLiveData.value?.indexOf(item)
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