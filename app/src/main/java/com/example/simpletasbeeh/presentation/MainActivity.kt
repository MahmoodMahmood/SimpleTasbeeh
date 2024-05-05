package com.example.simpletasbeeh.presentation

import android.os.Bundle
import android.view.HapticFeedbackConstants
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.TimeTextDefaults

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Scaffold(timeText = {
                TimeText(
                    timeTextStyle = TimeTextDefaults.timeTextStyle(
                        fontSize = 10.sp
                    )
                )
            }) {
                CounterScreen()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CounterScreen(viewModel: CounterViewModel = viewModel()) {
    val breakpoint = 33
    val count by viewModel.count.collectAsState()
    val view = LocalView.current
    val configuration = LocalConfiguration.current
    val surfaceColor = if (count >= breakpoint) MaterialTheme.colors.primary else Color.DarkGray

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Surface(
            color = surfaceColor,
            shape = CircleShape,
            modifier = Modifier
                .size(
                    width = (configuration.screenWidthDp * 0.8).dp,
                    height = (configuration.screenHeightDp * 0.8).dp

                )
                .combinedClickable(
                    onClick = {
                        if (count == breakpoint - 1) {
                            view.performHapticFeedback(HapticFeedbackConstants.REJECT)
                        }
                        viewModel.increment()
                    },
                    onLongClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                        viewModel.reset()
                    },
                ),
        ) {
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.title1,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
