package com.example.studycompose.practice

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

class LayoutActivity : AppCompatActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyComposeTheme() {
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
                    val modifier = Modifier.padding(innerPadding)
                    Column {
                        Row(
                            modifier = modifier
                                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                                .padding(16.dp)
                                .size(200.dp)
                                .horizontalScroll(rememberScrollState())

                        ) {
                            CardGrid(modifier = modifier) {
                                for (top in topics) {
                                    Chip(text = top, modifier = Modifier.padding(8.dp))
                                }
                            }
                        }
                        ConstraintLayoutPreview()
                        LargeConstraintLayout()
                        DecoupledConstraintLayout()
                        TwoText(text1 = "Hi", text2 = "There")
                    }
                }
            }
        }
    }
}


@Composable
fun ConstraintLayoutPreview() {
    ConstraintLayout {
        val (button1, button2, text) = createRefs()
        Button(onClick = { }, modifier = Modifier.constrainAs(button1) {
            top.linkTo(parent.top, margin = 10.dp)
        }) {
            Text(text = "Button1")
        }
        Text(text = "Compose", modifier = Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 10.dp)
            centerAround(button1.end)
        })
        val barrier = createEndBarrier(button1, text)
        Button(
            onClick = { /* Do something */ },
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 10.dp)
                start.linkTo(barrier)
            }
        ) {
            Text("Button 2")
        }
    }

}

@Preview
@Composable
fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()
        val guideline = createGuidelineFromStart(fraction = 0.5f)
        Text(

            text = "This is a very very very very very very very long text",

            Modifier.constrainAs(text) {
                start.linkTo(guideline)
                end.linkTo(parent.end)
                width = Dimension.preferredWrapContent
            }, maxLines = 2
        )


    }

}

@Composable
fun DecoupledConstraintLayout() {
    BoxWithConstraints() {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(margin = 16.dp) // Portrait constraints
        } else {
            decoupledConstraints(margin = 32.dp) // Landscape constraints
        }
        ConstraintLayout(constraints) {
            Button(
                onClick = { /* Do something */ },
                modifier = Modifier.layoutId("button")
            ) {
                Text("Button")
            }

            Text("Text", Modifier.layoutId("text"))
        }
    }

}

@Composable
fun TwoText(modifier: Modifier = Modifier, text1: String, text2: String) {
    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        Text(
            text = text1,
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.Start)
        )
        Divider(
            Modifier
                .fillMaxHeight()
                .width(1.dp), color = Color.Black
        )
        Text(
            text = text2,
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.End)
        )
    }

}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(parent.top, margin = margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)

fun Modifier.firstBaseLineTop(firstBaseLineTop: Dp) =
    this.then(layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseLine = placeable[FirstBaseline]
        val placeableY = firstBaseLineTop.roundToPx() - firstBaseLine
        val height = placeable.height + placeableY
        layout(placeable.width, height = height) {
            // Where the composable gets placed
            placeable.placeRelative(0, placeableY)
        }

    })

@Composable
fun CustomLayout(modifier: Modifier, content: @Composable () -> Unit) {

    Layout(modifier = modifier, content = content) { measurables, constraints ->

        val placeables = measurables.map { m ->
            m.measure(constraints = constraints)
        }
        var yPostion = 0
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { p ->
                p.placeRelative(0, yPostion)
                yPostion += p.height
            }
        }
    }
}

@Composable
fun CardGrid(modifier: Modifier, rows: Int = 3, content: @Composable () -> Unit) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val rowWidths = IntArray(rows) { 0 }
        val rowHeights = IntArray(rows) { 0 }

        val placeables = measurables.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constraints)
            val row = index % rows
            rowWidths[row] += placeable.width
            rowHeights[row] = max(rowHeights[row], placeable.height)
            placeable
        }

        // Grid's width is the widest row
        val width = rowWidths.maxOrNull()
            ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))
            ?: constraints.minWidth

        // Grid's height is the sum of the tallest element of each row
        // coerced to the height constraints
        val height = rowHeights.sumOf { it }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        // Y of each row, based on the height accumulation of previous rows
        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }
        layout(width, height) {
            // x cord we have placed up to, per row
            val rowX = IntArray(rows) { 0 }

            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(
                    x = rowX[row],
                    y = rowY[row]
                )
                rowX[row] += placeable.width
            }
        }


    }

}

@Composable
fun Chip(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(Modifier.width(4.dp))
            Text(text = text)
        }
    }
}




