package com.example.pppbtravel

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.pppbtravel.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //Date Picker
    private val calendar = Calendar.getInstance()

    //Transfer Data
    companion object {
        const val CREDENTIALS_USERNAME = "credentials_username"
        const val CREDENTIALS_PASSWORD = "credentials_password"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Date Picker
        val tanggalLahirButton = findViewById<Button>(R.id.tanggal_lahir_button)
        tanggalLahirButton.setOnClickListener() {
            showDatePicker()
        }

        //Transfer Data
        with(binding) {
            registerButton.setOnClickListener {
                if (usernameTxt.text.isEmpty()  ||
                    emailTxt.text.isEmpty()     ||
                    passwordTxt.text.isEmpty()
                ) {
                    val warningCredentials = "Please fill out the credentials."
                    Toast.makeText(this@MainActivity, warningCredentials, Toast.LENGTH_SHORT).show()
                } else {
                    if (tncCheckbox.isChecked) {
                        val username = usernameTxt.text.toString()
                        val password = passwordTxt.text.toString()

                        val intentToSecondActivity =
                            Intent(this@MainActivity, SecondActivity::class.java)
                        intentToSecondActivity.putExtra(CREDENTIALS_USERNAME, username)
                        intentToSecondActivity.putExtra(CREDENTIALS_PASSWORD, password)
                        startActivity(intentToSecondActivity)
                    } else {
                        val warningTNC = "Please accept the Terms and Conditions."
                        Toast.makeText(this@MainActivity, warningTNC, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            { DatePicker, year: Int, monthOfYear, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                binding.tanggalLahirButton.text = formattedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
        )
        datePickerDialog.show()
    }
}
