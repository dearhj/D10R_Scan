package com.scanner.d10r.hardware.base

import android.os.Bundle

open class BaseBackActivity : BaseActivity() {
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            this.setHomeButtonEnabled(true)
            this.setDisplayHomeAsUpEnabled(true)
        }
    }
}