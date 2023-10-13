package com.example.pppbtravel

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pppbtravel.databinding.ActivityThirdBinding
import com.example.pppbtravel.FourthActivity.Companion.PLAN_DATE
import com.example.pppbtravel.FourthActivity.Companion.PLAN_FROM
import com.example.pppbtravel.FourthActivity.Companion.PLAN_DESTINATION
import com.example.pppbtravel.FourthActivity.Companion.PLAN_CLASS
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding:ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Dari FourthActivity
        val rencanaTanggal = intent.getStringExtra(PLAN_DATE)
        val rencanaAsal = intent.getStringExtra(PLAN_FROM)
        val rencanaTujuan = intent.getStringExtra(PLAN_DESTINATION)
        val rencanaKelas = intent.getStringExtra(PLAN_CLASS)

        with(binding){
            asalShow.text = rencanaAsal
            tanggalShow.text = rencanaTanggal
            tujuanShow.text = rencanaTujuan
            paketPerjalananShow.text = rencanaKelas
            rencanaPerjalananButton.setOnClickListener {
                startActivity(Intent(this@ThirdActivity, FourthActivity::class.java))
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            calendar.init(
                calendar.year,
                calendar.month,
                calendar.dayOfMonth
            ){_, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                if (rencanaTanggal == formattedDate){
                    val warning = "Ada Rencana perjalanan"
                    Toast.makeText(this@ThirdActivity, warning, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}