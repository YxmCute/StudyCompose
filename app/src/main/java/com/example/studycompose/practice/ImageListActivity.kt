package com.example.studycompose.practice

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studycompose.ui.theme.StudyComposeTheme
import com.google.accompanist.coil.rememberCoilPainter
import kotlinx.coroutines.launch

class ImageListActivity : AppCompatActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppImageListDefaultPreview()
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MyAppImageList(content: @Composable () -> Unit) {
    StudyComposeTheme() {
        Surface(color = Color.White) {
            content()
        }

    }
}

@Preview("Scaffold preview")
@Composable
fun LayoutsCodeLab() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "LayoutsCodeLab") }, actions = {
                IconButton(
                    onClick = { }) {
                    Icon(Icons.Filled.Favorite, contentDescription = null)
                }
            })
        }
    ) { innerPadding ->
        BodyContent(Modifier.padding(innerPadding))
    }

}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    ImageList()
}

@Composable
fun ImageList() {
    val listSize = 100
    // We save the scrolling position with this state
    val scrollState = rememberLazyListState()
    // We save the coroutine scope where our animated scroll will be executed
    val coroutineScope = rememberCoroutineScope()
    Column {
        Row() {
            Button(onClick = {

                coroutineScope.launch {
                    scrollState.scrollToItem(0)
                }

            }) {
                Text(text = "Scroll to the top")
            }
            Button(onClick = {

                coroutineScope.launch {
                    scrollState.scrollToItem(listSize - 1)
                }

            }) {
                Text(text = "Scroll to the bottom")
            }
        }
        LazyColumn(state = scrollState) {
            items(listSize) {
                ImageListItem(index = it)
            }
        }
    }


}

@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberCoilPainter(request = "https://www.wanandroid.com/resources/image/pc/logo.png"),
            contentDescription = "WanAndroid Logo", modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = "Item index$index",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(start = 10.dp)
        )

    }
}

@ExperimentalFoundationApi
@Preview("DefaultPreview")
@Composable
fun MyAppImageListDefaultPreview() {
    MyApp {
        /* val photographer = Photographer(name = "Alfred Sisley", time = "3 minutes ago", "")
         PhotographerProfile(photographer = photographer)*/
        LayoutsCodeLab()
    }
}

@Composable
fun PhotographerProfile(photographer: Photographer) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable(onClick = {})
            .padding(16.dp)
    )
    {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {

        }
        /* Image(painter = PaintDrawable.createFromPath(), contentDescription = null)*/
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(alignment = Alignment.CenterVertically)
        ) {
            Text(text = photographer.name, fontWeight = FontWeight.Bold)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = photographer.time, style = MaterialTheme.typography.body2)
            }

        }
    }


}

data class Photographer(val name: String, val time: String, val avatar: String)


