package com.bignerdranch.android.petsaveapp.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bignerdranch.android.petsaveapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}