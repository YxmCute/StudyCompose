package com.example.studycompose.todo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studycompose.ui.theme.StudyComposeTheme
import kotlin.random.Random


class TodoActivity : AppCompatActivity() {
    private val viewModel by viewModels<TodoViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            StudyComposeTheme() {
                androidx.compose.material.Surface() {
                    ToDoActivityScreen(todoViewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun ToDoActivityScreen(todoViewModel: TodoViewModel) {
    val itemList: List<TodoItem> = todoViewModel.itemList
    ToDoScreen(
        todoItems = itemList,
        addItem = todoViewModel::addItem,
        deleteItem = todoViewModel::removeItem,
        onStartEdit = todoViewModel::onEditItemSelected,
        onItemChanged = todoViewModel::onEditItemChange,
        onEditDone = todoViewModel::onEditDone
    )


}

@Composable
fun ToDoScreen(
    todoItems: List<TodoItem>,
    addItem: (TodoItem) -> Unit,
    deleteItem: (TodoItem) -> Unit,
    onStartEdit: (TodoItem) -> Unit,
    onItemChanged: (TodoItem) -> Unit,
    onEditDone: () -> Unit
) {
    Column() {
        TodoItemEntryInput {
            addItem(it)
        }
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(todoItems) {
                TodoRow(
                    todoItem = it,
                    onclick = { deleteItem(it) },
                    modifier = Modifier.fillParentMaxWidth()
                )
            }
        }
        Button(
            onClick = { addItem(generateRandomTodoItem()) },
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 20.dp)
        ) {
            Text(text = "Add random Item")

        }
    }


}

@Composable
fun TodoItemEditInlineEditor(
    todoItem: TodoItem,
    onEditItemChange: (TodoItem) -> Unit,
    onEditDone: () -> Unit,
    onRemove: () -> Unit
) {

    TodoInput(
        text = todoItem.task,
        setText = { onEditItemChange(todoItem.copy(task = it)) },
        submit = { /*TODO*/ },
        iconChange =true
    )

}

@Composable
fun TodoRow(
    todoItem: TodoItem,
    onclick: (TodoItem) -> Unit,
    modifier: Modifier = Modifier,
    iconAlpha: Float = remember(todoItem.id) { randomTint() }
) {
    Row(modifier = modifier
        .clickable {
            onclick(todoItem)

        }
        .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = todoItem.task)

        Icon(
            todoItem.icon.imageVector,
            tint = LocalContentColor.current.copy(alpha = iconAlpha),
            contentDescription = stringResource(id = todoItem.icon.contentDescription)
        )

    }

}


@Composable
fun TodoInputTextField(
    text: String, setText: (String) -> Unit, modifier: Modifier, onImeAction: () -> Unit = {
    }
) {
    TodoInputText(text, setText, modifier, onImeAction)
}

@Preview
@Composable
fun PreviewTodoItemInput() = TodoItemEntryInput(onItemComplete = { })

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TodoInputText(
    text: String,
    setText: (String) -> Unit,
    modifier: Modifier,
    onImeAction: () -> Unit = {
    }
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = text,
        onValueChange = setText,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
        }),
        modifier = modifier
    )
}

@Composable
fun TodoItemEntryInput(onItemComplete: (TodoItem) -> Unit) {
    // onItemComplete is an event will fire when an item is completed by the user
    val (text, setText) = remember { mutableStateOf("") }
    val (icon, setIcon) = remember {
        mutableStateOf(TodoIcon.Default)
    }
    val submit = {
        onItemComplete(TodoItem(text, icon))
        setIcon(TodoIcon.Default)
        setText("")
    }
    val iconsVisible = text.isNotBlank()
    TodoInput(text, setText, submit, iconsVisible)
}

@Composable
fun TodoInput(
    text: String,
    setText: (String) -> Unit,
    submit: () -> Unit,
    iconChange: Boolean
) {
    Column {
        Row(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            TodoInputTextField(
                text = text,
                setText = setText,
                Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            TodoEditButton(
                onClick = submit// clear the internal text) ,
                , text = "Add",
                modifier = Modifier.align(Alignment.CenterVertically), enabled = text.isNotBlank()
            )
            if (iconChange) {
                // AnimatedIconRow(icon, setIcon, Modifier.padding(top = 8.dp))
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun TodoEditButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    TextButton(
        onClick = onClick,
        shape = CircleShape,
        enabled = enabled,
        modifier = modifier
    ) {
        Text(text)
    }
}


private fun randomTint(): Float {
    return Random.nextFloat().coerceIn(0.3f, 0.9f)
}