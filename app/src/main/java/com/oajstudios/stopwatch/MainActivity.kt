package com.oajstudios.stopwatch

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Black
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oajstudios.stopwatch.ui.StopWatch
import com.oajstudios.stopwatch.ui.theme.StopwatchTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StopwatchTheme {
                // A surface container using the 'background' color from the theme
                Surface{
                    MainApp()
                }

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainApp() {
     val stopWatch = remember {
         StopWatch()
     }

    MainApp(
        isPlaying = stopWatch.isPlaying,
        isReset = stopWatch.isReset,
        formattedTime = stopWatch.formattedTime,
        onStart = stopWatch::start,
        onPause = stopWatch::stop,
        onStop = stopWatch::reset,
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun MainApp(
    isPlaying: Boolean,
    isReset: Boolean,
    formattedTime: String,
    onStart: () -> Unit = {},
    onPause: () -> Unit = {},
    onStop: () -> Unit = {},

    ){
    Scaffold {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.weight(1f))
            Row {
                Text(
                    text = formattedTime,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.background(Color.LightGray, RoundedCornerShape(50))
            ){
                AnimatedContent(targetState = isPlaying) {
                    if (it){
                        Button(onClick = onPause){
                            Text("Stop")
                        }
                    } else if (isReset) {
                        Button(onClick = onStop){
                            Text("Start")
                        }
                     } else {
                         Button(onClick = onStart){
                             Text("Start")
                         }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StopwatchTheme {
        MainApp(
            false,
            false,
             "00",
        )
    }
}