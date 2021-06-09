package com.example.studycompose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.studycompose.ui.theme.StudyComposeTheme
import com.google.accompanist.coil.CoilImage

class MainActivity : AppCompatActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DefaultPreview()
            // NewStory()
            /* StudyComposeTheme() {
                 Surface(color = Color.Blue) {
                     Greeting(name = "lijun")
                 }
             }*/
            /* LayoutCodeLab()*/
            /* StudyComposeTheme() {
                 Surface() {
                     ImageList()
                 }
             }*/

            /* StudyComposeTheme {
                 // A surface container using the 'background' color from the theme
                 *//*Surface(color = MaterialTheme.colors.background) {

                }*//*
               // Card()

            }*/
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
    var color = Color.Green
    Surface(color = Color.Cyan) {
        Text(
            text = "Hello $name!",
            modifier = Modifier
                .border(
                    border = BorderStroke(3.dp, color = Color(0xFFE91E63)),
                    shape = RoundedCornerShape(10.dp)
                )
                .combinedClickable(
                    onClick = {
                        color = Color.Black
                    },
                    onDoubleClick = { color = Color.Red }
                ),
            fontSize = 20.sp,
            fontFamily = FontFamily.Monospace,
            fontStyle = FontStyle.Italic,
            letterSpacing = 0.em, textDecoration = TextDecoration.LineThrough


        )
    }

}

@ExperimentalFoundationApi
@Composable
fun MyScreenContent(list: List<String> = listOf("Android", "there", "didododo")) {
    Column {
        for (name in list) {
            Greeting(name = name)
            Divider(color = Color.Black)
        }
        Divider(color = Color.Transparent, thickness = 32.dp)
        Counter()
    }

}

@Composable
fun Counter() {
    val count = remember { mutableStateOf(0) }
    Button(onClick = { count.value++ }) {
        Text(text = "I`ve been clicked ${count.value} times")
    }
}

@ExperimentalFoundationApi
@Preview("MyScreen preview")
@Composable
fun DefaultPreview() {
    MyApp {
        MyScreenContent()
    }
}

/*
@ExperimentalFoundationApi
@Preview
@Composable
fun PreviewGreeting() {
    Greeting(name = "lijun")

}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StudyComposeTheme {
        Greeting("Android")
    }
}

@Preview()
@Composable
fun LayoutCodeLab() {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "LayoutsCodelab", style = MaterialTheme.typography.h3) },
            actions = {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
                }
            })

    }) { innerPadding ->


        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            Text(text = "Hi,here", modifier = Modifier.padding(innerPadding))
            Text(
                text = "Thanks for going through the Layouts codelab",
                modifier = Modifier.padding(innerPadding)
            )
        }


    }
}

@ExperimentalFoundationApi
@Preview(showBackground = false)
@Composable
fun Card() {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(color = MaterialTheme.colors.surface)
            .clickable(onClick = {
            })
            .padding(16.dp)
    ) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(0.2f)
        ) {

        }

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = "Alfred Sisley", fontWeight = FontWeight.Bold)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = "3 minutes ago", style = MaterialTheme.typography.body2)
            }

        }
    }

}

@Composable
fun SampleList() {
    val scrollState = rememberScrollState()
    Column(Modifier.verticalScroll(scrollState)) {
        repeat(100) {
            Text("Item #$it")
        }

    }


}

@Composable
fun LazyList() {
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        items(100) { Text("Item #$it") }
    }


}

@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CoilImage(
            data = "https://developer.android.google.cn/images/brand/Android_Robot.png",
            contentDescription = "Android Logo ",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text("Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun ImageList() {
    LazyColumn {
        items(100) {
            ImageListItem(index = it)
        }
    }

}*/
