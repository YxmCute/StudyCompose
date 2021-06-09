package com.example.studycompose.practice

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studycompose.R

class BasicActivity : AppCompatActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewStory()
        }
    }
}
@Preview
@Composable
fun NewStory() {
    Column(modifier = Modifier.padding(16.dp)) {
        Image(
            painter = painterResource(id = R.drawable.header), contentDescription = null,
            modifier = Modifier
                .height(180.dp)
                .clip(shape = RoundedCornerShape(4.dp))
                .fillMaxWidth(), contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "A day wandering through the sandhills in Shark Fin Cove, and a few of the sights I saw",
            style = typography.h6,
            maxLines = 2, overflow = TextOverflow.Ellipsis
        )
        Text("Davenport, California", style = typography.body1)
        Text("December 2018", style = typography.body2)
    }
}


