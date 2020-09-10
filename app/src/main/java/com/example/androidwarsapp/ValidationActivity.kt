package com.example.androidwarsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ValidationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validation)
        this.title = "Validation screen"
    }
}