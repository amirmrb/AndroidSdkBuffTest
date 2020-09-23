package com.buffup.buffsdk.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (layout == 0) throw IllegalArgumentException("layout parameter should be valuated before launching activity")
        setContentView(layout)
    }

    companion object {
        var layout = 0
    }
}