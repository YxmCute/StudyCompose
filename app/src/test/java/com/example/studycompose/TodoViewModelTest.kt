package com.example.studycompose

import com.example.studycompose.todo.TodoViewModel
import com.example.studycompose.todo.generateRandomTodoItem
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TodoViewModelTest {
    @Test
    fun whenRemovingItem_updatesList() {
        // before
        val viewModel = TodoViewModel()
        val item1 = generateRandomTodoItem()
        val item2 = generateRandomTodoItem()
        viewModel.addItem(item1)
        viewModel.addItem(item2)

        // during
        viewModel.removeItem(item1)

        // after
        assertThat(viewModel.itemList).isEqualTo(listOf(item2))
    }
}