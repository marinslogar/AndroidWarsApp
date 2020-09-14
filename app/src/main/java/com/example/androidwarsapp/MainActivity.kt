package com.example.androidwarsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * Class for the main activity
 */
class MainActivity : AppCompatActivity() {

    /**
     * On create function in which we set up the support action bar with back button
     * so we can return to the validation activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.title = "Main screen"

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Function that is called whenever the user chooses to navigate up withing the application's
     * activity hierarchy from the action bar
     * @return true if up navigation completed successfully
     */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}