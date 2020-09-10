package com.example.androidwarsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RngActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rng)
        this.title = "RNG screen"
    }
}