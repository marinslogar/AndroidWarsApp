package com.example.androidwarsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_validation.*

class ValidationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validation)
        this.title = "Validation screen"

//        languageButton.setOnClickListener {
//
//        }
//
//        loginButton.setOnClickListener {
//
//        }

        rngButton.setOnClickListener {
            println(validationTextField.text)
            val intent = Intent(this, RngActivity::class.java).apply {
                putExtra("validationTextField", validationTextField.text.toString())
            }
            startActivity(intent)
        }

        val randomNumber = intent.getIntExtra("randomNumber", 0)
        validationTextField.setText(randomNumber.toString())
    }
}