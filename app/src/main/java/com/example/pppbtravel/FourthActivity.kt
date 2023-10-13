package com.example.pppbtravel

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import com.example.pppbtravel.databinding.ActivityFourthBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FourthActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFourthBinding
    private var destination = R.array.destination
    private var kelasKereta = R.array.kelas
    //Date Picker
    private val calendar = Calendar.getInstance()

    companion object{
        const val PLAN_DATE = "plan_date"
        const val PLAN_FROM = "plan_from"
        const val PLAN_DESTINATION = "plan_destination"
        const val PLAN_CLASS = "plan_class"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFourthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Date Picker
        val tanggalRencana = findViewById<Button>(R.id.tanggal_rencana)
        tanggalRencana.setOnClickListener() {
            showDatePicker()
        }

        with(binding){
            //SetUp Spinner Asal
            val asal = resources.getStringArray(destination)
            val adapterAsal = ArrayAdapter<String>(this@FourthActivity,
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
                asal)
            asalSpinner.adapter = adapterAsal
            //SetUp Spinner Tujuan
            val tujuan = resources.getStringArray(destination)
            val adapterTujuan = ArrayAdapter<String>(this@FourthActivity,
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
                tujuan)
            tujuanSpinner.adapter = adapterTujuan
            //SetUp Spinner Kelas Kereta
            val kelas = resources.getStringArray(kelasKereta)
            val adapterKelas = ArrayAdapter<String>(this@FourthActivity,
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
                kelas)
            kelasKeretaSpinner.adapter = adapterKelas

            bookButton.setOnClickListener {
                //Mengambil nilai
                val selectedTanggal = binding.tanggalRencana.text.toString()
                val selectedAsal = asalSpinner.selectedItem.toString()
                val selectedTujuan = tujuanSpinner.selectedItem.toString()
                val selectedKelas = kelasKeretaSpinner.selectedItem.toString()

                val intentToThirdActivity =
                    Intent(this@FourthActivity,
                        ThirdActivity::class.java
                    )
//                val name = editText1.text.toString()
                intentToThirdActivity.putExtra(PLAN_DATE, selectedTanggal)
                intentToThirdActivity.putExtra(PLAN_FROM, selectedAsal)
                intentToThirdActivity.putExtra(PLAN_DESTINATION, selectedTujuan)
                intentToThirdActivity.putExtra(PLAN_CLASS, selectedKelas)
                startActivity(intentToThirdActivity)
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
                binding.tanggalRencana.text = formattedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
        )
        datePickerDialog.show()
    }
}