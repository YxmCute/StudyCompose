package com.example.studycompose.practice

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.studycompose.ui.theme.StudyComposeTheme

class MainActivity : AppCompatActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DefaultPreview()
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MyApp(content: @Composable () -> Unit) {
    StudyComposeTheme() {
        Surface(color = Color.Yellow) {
            content()
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun Greeting(name: String) {
    var isSelected by remember { mutableStateOf(false) }
    val bgColor by animateColorAsState(if (isSelected) Color.Green else Color.Gray)
    Text(
        text = "Hello $name!",
        modifier = Modifier
            .padding(24.dp)
            .border(
                border = BorderStroke(1.dp, color = Color(0xFFE91E63)),
                shape = RoundedCornerShape(10.dp)
            )
            .background(color = bgColor)
            .clickable(onClick = { isSelected = !isSelected }),
        fontSize = 20.sp,
        fontFamily = FontFamily.Monospace,
        fontStyle = FontStyle.Italic,
        letterSpacing = 0.em, textDecoration = TextDecoration.None,
        style = MaterialTheme.typography.h1
    )

}

@ExperimentalFoundationApi
@Composable
fun MyScreenContent(list: List<String> = listOf("Android", "there", "didododo")) {
    val count = remember { mutableStateOf(0) }
    Column(Modifier.fillMaxHeight()) {
        Column(Modifier.weight(1f)) {
            for (name in list) {
                Greeting(name = name)
                Divider(color = Color.Blue, thickness = 32.dp)
            }
        }
        Counter(count.value, updateCount = { new ->
            count.value = new
        })
    }

}

@Composable
fun Counter(counter: Int, updateCount: (Int) -> Unit) {

    Button(
        onClick = { updateCount(counter + 1) },
        colors = ButtonDefaults.buttonColors(backgroundColor = if (counter < 5) Color.Green else Color.White)
    ) {
        Text(text = "I`ve been clicked $counter times")
    }
}

@ExperimentalFoundationApi
@Composable
fun MyScreenNameList() {
    val list = List<String>(1000) { "Hello Compose $it" }
    NameList(names = list, modifier = Modifier.padding(1.dp))

}

@ExperimentalFoundationApi
@Composable
fun NameList(names: List<String>, modifier: Modifier) {
    LazyColumn(modifier) {
        items(names) { name ->
            Greeting(name = name)
            Divider(color = Color.Black)
        }
    }

}

@ExperimentalFoundationApi
@Preview("MyScreen preview")
@Composable
fun DefaultPreview() {
    MyApp {
        MyScreenNameList()
    }
}

