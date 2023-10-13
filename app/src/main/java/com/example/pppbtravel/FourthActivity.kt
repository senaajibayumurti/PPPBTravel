package com.example.pppbtravel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pppbtravel.databinding.ActivityFourthBinding

class FourthActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFourthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFourthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}