package com.example.recyclerproject

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recyclerproject.Data.Item
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity()
{
    private val itemListViewModel by viewModels<ItemsListViewModel>{
        ItemsListViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val itemAdapter = ItemAdapter {item, position -> adapterOnClick(item, position)}

        recycler_view.adapter = itemAdapter

        recycler_view.apply {
            layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                GridLayoutManager(this@MainActivity, 2)
            } else {
                GridLayoutManager(this@MainActivity, 4)
            }
        }

        itemListViewModel.itemLiveData.observe(this){
            it.let {
                itemAdapter.submitList(it as MutableList<Item>)
            }
        }

        pushItemAsync()
    }

    private fun adapterOnClick(item: Item, position: Int){
        itemListViewModel.removeItem(item)
    }

    private fun pushItemAsync() = GlobalScope.async {
        while(true) {
            delay(5000)
            itemListViewModel.insertItem()
        }
    }

}

