package com.example.studycompose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.studycompose.ui.theme.StudyComposeTheme
import kotlin.math.max

class MainActivity : AppCompatActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DDefaultPreview()
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MyApp(content: @Composable () -> Unit) {
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
    BoxWithConstraints (modifier=Modifier.background(Color.Blue)){

        Text("My minHeight is $maxHeight while my maxWidth is $maxWidth")
    }
}


@ExperimentalFoundationApi
@Preview("DefaultPreview")
@Composable
fun DDefaultPreview() {
    MyApp {
        /* val photographer = Photographer(name = "Alfred Sisley", time = "3 minutes ago", "")
         PhotographerProfile(photographer = photographer)*/
        LayoutsCodeLab()
    }
}



