package com.example.recyclerproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerproject.Data.Item
import java.util.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val itemListViewModel by viewModels<ItemsListViewModel>{
        ItemsListViewModelFactory(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemAdapter = ItemAdapter {item ->  adapterOnClick(item)}


        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = itemAdapter

        recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
        }

        itemListViewModel.itemLiveData.observe(this){
            it?.let {
                itemAdapter.submitList(it as MutableList<Item>)
            }
        }
        pushItemAsync()
    }


    private fun adapterOnClick(item: Item){
        itemListViewModel.removeItem(item)
    }

    private fun pushItemAsync() = GlobalScope.async {
        while(true) {
                delay(5000)
                itemListViewModel.insertItem()
        }
    }

}