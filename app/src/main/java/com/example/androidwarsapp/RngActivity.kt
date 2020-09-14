package com.example.androidwarsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_rng.*

/**
 * Class for the RNG activity
 * It contains a textfield and 2 buttons
 */
class RngActivity : AppCompatActivity() {
    private var randomNumber : Int = 0

    /**
     * On create function which deals with setting up the activity and the buttons
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rng)
        this.title = "RNG screen"

        //Get the string from the validation text field and set it to the rng text field
        val stringFromValidation = intent.getStringExtra("validationTextField")
        rngTextField.text = stringFromValidation

        //On generate button click, generate random number and save it into a local variable
        generateButton.setOnClickListener {
            randomNumber = generateRandomNumber()
        }

        //On close button click, go back to validation activity and send the value from the textfield with intent
        closeButton.setOnClickListener {
            val intent = Intent(this, ValidationActivity::class.java).apply {
                putExtra("randomNumber", randomNumber.toString())
            }
            startActivity(intent)
        }
    }

    /**
     * A function to generate random number between some range
     * @return random number between 10 and 99
     */
    private fun generateRandomNumber(): Int {
        return (10..99).random()
    }
}