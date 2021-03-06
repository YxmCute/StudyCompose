package com.example.studycompose.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {
    private var currentEditPosition by mutableStateOf(-1)

    var itemList by mutableStateOf(listOf<TodoItem>())
        private set

    fun addItem(item: TodoItem) {
        itemList = itemList + listOf(item)
    }

    fun removeItem(item: TodoItem) {
        itemList.toMutableList().also {
            it.remove(item)
        }
        onEditDone()

    }

    val currentEditItem: TodoItem?
        get() = itemList.getOrNull(currentEditPosition)

    fun onEditItemSelected(item: TodoItem) {
        currentEditPosition = itemList.indexOf(item)
    }

    fun onEditDone() {
        currentEditPosition = -1
    }

    // event: onEditItemChange
    fun onEditItemChange(item: TodoItem) {
        val currentItem = requireNotNull(currentEditItem)
        require(currentItem.id == item.id) {
            "You can only change an item with the same id as currentEditItem"
        }

        itemList = itemList.toMutableList().also {
            it[currentEditPosition] = item
        }
    }
}