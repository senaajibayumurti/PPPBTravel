package com.example.pppbtravel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pppbtravel.databinding.ActivitySecondBinding
import com.example.pppbtravel.MainActivity.Companion.CREDENTIALS_USERNAME
import com.example.pppbtravel.MainActivity.Companion.CREDENTIALS_PASSWORD

class SecondActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            val userUsername = intent.getStringExtra(CREDENTIALS_USERNAME)
            val userPassword = intent.getStringExtra(CREDENTIALS_PASSWORD)

            loginButton.setOnClickListener {
                val username = usernameTxt.text.toString()
                val password = passwordTxt.text.toString()

                if (username == userUsername && password == userPassword ){
                    startActivity(Intent(this@SecondActivity, ThirdActivity::class.java))
                }
                else{
                    val warningCredentials = "Credentials are not match."
                    Toast.makeText(this@SecondActivity, warningCredentials, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}