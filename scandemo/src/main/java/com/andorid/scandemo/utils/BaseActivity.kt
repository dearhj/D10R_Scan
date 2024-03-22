package com.andorid.scandemo.utils

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val supportActionBar = supportActionBar
        if (supportActionBar != null) {
            supportActionBar.setHomeButtonEnabled(true)
            supportActionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    fun showToast(message: String) {
        runOnUiThread {
            try {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}