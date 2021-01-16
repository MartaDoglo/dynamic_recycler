package com.example.recyclerproject.Data

fun itemList(): MutableList<Item> {
    val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    var items: MutableList<Item> = mutableListOf()
    for (i in 1..15){
        val newItem = Item(
                i.toLong(),
                List(6) { alphabet.random() }.joinToString("")
        )
        items.add(newItem)
    }
    return items
}

