package com.example.androidwarsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_rng.*

class RngActivity : AppCompatActivity() {
    var randomNumber : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rng)
        this.title = "RNG screen"

        val stringFromValidation = intent.getStringExtra("validationTextField")
        rngTextField.text = stringFromValidation

        generateButton.setOnClickListener {
            randomNumber = generateRandomNumber()
        }

        closeButton.setOnClickListener {
            val intent = Intent(this, ValidationActivity::class.java).apply {
                putExtra("randomNumber", randomNumber)
            }
            startActivity(intent)
        }
    }

    fun generateRandomNumber(): Int {
        return (10..99).random()
    }
}