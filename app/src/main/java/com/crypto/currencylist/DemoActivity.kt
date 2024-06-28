package com.crypto.currencylist

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.crypto.currencylist.databinding.ActivityDemoBinding

class DemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}