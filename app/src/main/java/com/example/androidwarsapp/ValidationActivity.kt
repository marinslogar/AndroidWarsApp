package com.example.androidwarsapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_validation.*
import kotlinx.coroutines.*
import java.util.*
import java.util.regex.Pattern

/**
 * Class for the validation activity which is the starting activity of this application
 * It contains a textfield and 3 buttons
 */
class ValidationActivity : AppCompatActivity() {

    /**
     * On create function which deals with setting up the activity and the buttons
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocale()
        setContentView(R.layout.activity_validation)
        this.title = "Validation screen"

        //On language button click, call the changeLanguage() function
        languageButton.setOnClickListener {
            changeLanguage()
        }

        //On login button click, call the validate() function
        loginButton.setOnClickListener {
            validate()
        }

        //On rng button click, go to the rng activity and send the value from the textfield with intent
        rngButton.setOnClickListener {
            val intent = Intent(this, RngActivity::class.java).apply {
                putExtra("validationTextField", validationTextField.text.toString())
            }
            startActivity(intent)
        }

        //Get the string from the rng text field and set it to the validation text field
        val randomNumber = intent.getStringExtra("randomNumber")
        validationTextField.setText(randomNumber)
    }

    /**
     * Function which is responsible for validating the value from the validationTextField
     * Validation is done in background process with the help of coroutine library
     */
    private fun validate() = runBlocking<Unit> {
        launch {
            GlobalScope.launch {
                //length should be greater than 4 and less than 14 + It must not contain character ‘?’
                val lengthExclude = Regex("""^[aA-zZ][^?]{4,14}${'$'}""")
                val lengthExcludeValue = lengthExclude.find(validationTextField.text)?.value

                //Letter ‘a’ (or ‘A’) should appear at least 2 or more times
                val occurrenceOfA = countOccurrences(validationTextField.text.toString())

                //Value haves exactly one character (number) ‘7’
                val exactlyOne = Regex("""^[^7]*7[^7]*${'$'}""")
                val exactlyOneValue = exactlyOne.find(validationTextField.text)?.value

                //If ‘b’ is contained within the value, character before b shouldn’t be a number
                //First check if char 'b' exists in the string
                val checkForB = Regex("""[b]+""")
                val checkForBValue = checkForB.find(validationTextField.text)?.value

                var containsBValue: String? = ""

                //If char 'b' exists in the string, validate that char before 'b' is a string
                if (checkForBValue != null) {
                    val containsB = Regex("""[aA-zZ][b]""")
                    containsBValue = containsB.find(validationTextField.text)?.value
                }

                //if all values are not null and occurance of a is >=2, start main activity with intent
                if (occurrenceOfA != null) {
                    if(lengthExcludeValue != null && occurrenceOfA >= 2 && exactlyOneValue != null && containsBValue != null) {
                        val intent = Intent(this@ValidationActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    /**
     * Function to count occurrences of char 'a' in a string
     * @return number of occurrences of char 'a'
     */
    private fun countOccurrences(s: String): Int? {
        val matcher = Pattern.compile('a'.toString()).matcher(s)
        var counter = 0
        while (matcher.find()) counter++
        return counter
    }

    /**
     * Function for changing the language of the application to English/Croatian
     */
    private fun changeLanguage() {
        val languageArray = arrayOf("English", "Croatian")
        val mBuilder = AlertDialog.Builder(this@ValidationActivity)
        mBuilder.setTitle("Change language")
        mBuilder.setSingleChoiceItems(languageArray, -1) { dialogInterface, i ->
            if(i == 0) {
                setLocale("en")
                recreate()
            } else if(i == 1) {
                setLocale("hr")
                recreate()
            }
            dialogInterface.dismiss()
            Toast.makeText(this, "Language updated", Toast.LENGTH_LONG).show()
        }
        mBuilder.setNeutralButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        val mDialog = mBuilder.create()
        mDialog.show()
    }

    /**
     * Function for setting the default locale
     */
    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()
    }

    /**
     * Function for loading the local language
     */
    private fun loadLocale() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        if (language != null) {
            setLocale(language)
        }
    }
}