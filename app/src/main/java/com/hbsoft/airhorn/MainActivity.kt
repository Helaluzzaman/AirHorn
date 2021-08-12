package com.hbsoft.airhorn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator

    }
}