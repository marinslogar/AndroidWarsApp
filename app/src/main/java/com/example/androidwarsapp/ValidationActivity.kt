package com.example.androidwarsapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_validation.*
import java.util.*
import java.util.regex.Pattern

class ValidationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocale()
        setContentView(R.layout.activity_validation)
        this.title = "Validation screen"

        languageButton.setOnClickListener {
            changeLanguage()
        }
//
        loginButton.setOnClickListener {
            val lengthExclude = Regex("""^[aA-zZ]{4,14}[^?]${'$'}""")
            val lengthExcludeValue: String? = lengthExclude.find(validationTextField.text)?.value
            //println(lengthExcludeValue)

            val occuranceOfA = countOccurrences(validationTextField.text.toString(), 'a')
            //println(occuranceOfA)

            val exactlyOne = Regex("""^[^7]*7[^7]*${'$'}""")
            val exactlyOneValue: String? = exactlyOne.find(validationTextField.text)?.value
            //println(exactlyOneValue)

            val containsB = Regex("""[aA-zZ][b]""")
            val containsBValue: String? = containsB.find(validationTextField.text)?.value
            println("contains$containsBValue")

            if(lengthExcludeValue != null && occuranceOfA >=2 && exactlyOneValue != null && containsB != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        rngButton.setOnClickListener {
            val intent = Intent(this, RngActivity::class.java).apply {
                putExtra("validationTextField", validationTextField.text.toString())
            }
            startActivity(intent)
        }

        val randomNumber = intent.getIntExtra("randomNumber", 0)
        validationTextField.setText(randomNumber.toString())
    }

    fun countOccurrences(s: String, ch: Char): Int {
        val matcher = Pattern.compile(ch.toString()).matcher(s)
        var counter = 0
        while (matcher.find()) counter++
        return counter
    }

    fun changeLanguage() {
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
        mBuilder.setNeutralButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        val mDialog = mBuilder.create()
        mDialog.show()
    }

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

    private fun loadLocale() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        if (language != null) {
            setLocale(language)
        }
    }
}