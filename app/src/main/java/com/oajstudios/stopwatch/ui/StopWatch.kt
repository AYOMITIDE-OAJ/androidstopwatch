package com.oajstudios.stopwatch.ui

import android.os.Build

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class StopWatch {

    var formattedTime by mutableStateOf("00:00:00")
    var isPlaying by mutableStateOf(false)
    var isReset by mutableStateOf(false)

    private var coroutineScope = CoroutineScope(Dispatchers.Main)
    private var isActive = false

    private var timeMillis = 0L
    private var lastTimestamp = 0L


    fun start (){
        if (isActive) return

        coroutineScope.launch {

            lastTimestamp = System.currentTimeMillis()
            this@StopWatch.isActive = true

            while(this@StopWatch.isActive){
                delay(10L)
                timeMillis += System.currentTimeMillis() - lastTimestamp
                lastTimestamp = System.currentTimeMillis()
                formattedTime = formatTime(timeMillis)
            }

        }
        isPlaying = true
        isReset = false
    }


    private fun formatTime(timeMillis: Long): String {
        val localDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeMillis),
            ZoneId.systemDefault()
        )

        val formatter = DateTimeFormatter.ofPattern(
            "mm:ss:SS",
            Locale.getDefault()
        )
        return localDateTime.format(formatter)
    }

    fun stop(){
        isActive = false
        isReset = true
        isPlaying = false
    }


    fun reset(){
        timeMillis = 0L
        lastTimestamp = 0L
        formattedTime =  "00:00:00"
        isReset = false
        isActive = false
        start();
    }
}