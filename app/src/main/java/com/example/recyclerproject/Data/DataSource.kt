package com.example.recyclerproject.Data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DataSource {
    private val initialItemList  = itemList()
    private val itemLiveData = MutableLiveData(initialItemList)

    private val freeId: MutableList<Long> = mutableListOf()

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
            itemLiveData.postValue(mutableListOf(item))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(position, item)
            itemLiveData.postValue(updatedList)
        }
    }

    fun removeItem(item: Item){
        freeId.add(item.id)
        freeId.sort()
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

    fun getItemList(): LiveData<MutableList<Item>>{
        return itemLiveData
    }

    companion object {
        private var INSTANCE: DataSource? = null
        fun getDataSource(): DataSource{
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource()
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}