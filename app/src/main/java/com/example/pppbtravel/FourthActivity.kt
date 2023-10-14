package com.example.pppbtravel

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.pppbtravel.databinding.ActivityFourthBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.widget.Switch

class FourthActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFourthBinding
    private var from = R.array.from
    private var destination = R.array.destination
    private var kelasKereta = R.array.kelas
    private var hargaDef = 0.0
    private var hargaSementara = 0.0
    private var hargaPesanan = 0.0

    //Date Picker
    private val calendar = Calendar.getInstance()

    // Formatting harga ke Rp
    val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))

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
        // Buat ngitung switch
        val switches = arrayOf(
            findViewById<Switch>(R.id.switch_tambahan_1),
            findViewById<Switch>(R.id.switch_tambahan_2),
            findViewById<Switch>(R.id.switch_tambahan_3),
            findViewById<Switch>(R.id.switch_tambahan_4),
            findViewById<Switch>(R.id.switch_tambahan_5),
            findViewById<Switch>(R.id.switch_tambahan_6),
            findViewById<Switch>(R.id.switch_tambahan_7),
            findViewById<Switch>(R.id.switch_tambahan_8)
        )

        //Menyembunyikan Kelas
        val hidingSpinner =findViewById<Spinner>(R.id.kelas_kereta_spinner)
        hidingSpinner.visibility = View.GONE

        //Date Picker
        val tanggalRencana = findViewById<Button>(R.id.tanggal_rencana)
        tanggalRencana.setOnClickListener() {
            showDatePicker()
        }

        // Hitung jumlah switch yang aktif
        var jumlahSwitchAktif = 0

        with(binding){
            //SetUp Spinner Asal
            val asal = resources.getStringArray(from)
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

            // Pasangan tujuan dan harga
            val pasanganHarga = HashMap<String, Double>()
            pasanganHarga["Stasiun Tugu, Sleman - Stasiun Wonosobo, Wonosobo"] = 90000.0
            pasanganHarga["Stasiun Wonosobo, Wonosobo - Stasiun Tugu, Sleman"] = 90000.0
            pasanganHarga["Stasiun Tugu, Sleman - Stasiun Cukir, Ndhiwek"] = 170000.0
            pasanganHarga["Stasiun Cukir, Ndhiwek - Stasiun Tugu, Sleman"] = 170000.0
            pasanganHarga["Stasiun Wonosobo, Wonosobo - Stasiun Cukir, Ndhiwek"] = 240000.0
            pasanganHarga["Stasiun Cukir, Ndhiwek - Stasiun Wonosobo, Wonosobo"] = 240000.0

            //Mengatur Item Spinner
            asalSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    // Penggabungan item
                    val selectedAsal = asalSpinner.selectedItem.toString()
                    val selectedTujuan = tujuanSpinner.selectedItem.toString()
                    val pasangan = "$selectedAsal - $selectedTujuan"
                    val hargaPasangan = pasanganHarga[pasangan]

                    if (position == 0) {
                        val textView = view as? TextView
                        textView?.setTextColor(resources.getColor(R.color.grey))
                        hargaPesanan = hargaDef
                        updateHargaDinamis()
                    } else if (selectedAsal == selectedTujuan) {
                        Toast.makeText(this@FourthActivity, "Ada kesalahan pemesanan, cuy", Toast.LENGTH_SHORT).show()
                        hargaPesanan = hargaDef
                        updateHargaDinamis()
                    } else {
                        val textView = view as? TextView
                        textView?.setTextColor(resources.getColor(android.R.color.black))

                        if (hargaPasangan != null){
                            if (position == 1 && kelasKeretaSpinner.selectedItemPosition != 0){
                                hargaPemesanan(hargaPasangan)
                                updateHargaDinamis()
                            }
                            else if (position == 2 && kelasKeretaSpinner.selectedItemPosition != 0){
                                var hargaBisnis = hargaPasangan * 1.5
                                hargaPemesanan(hargaBisnis)
                                updateHargaDinamis()
                            }
                            else if (position == 3 && kelasKeretaSpinner.selectedItemPosition != 0){
                                var hargaEksekutif = hargaPasangan * 2.0
                                hargaPemesanan(hargaEksekutif)
                                updateHargaDinamis()
                            }
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Nothing to do here
                }
            })
            tujuanSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    // Penggabungan item
                    val selectedAsal = asalSpinner.selectedItem.toString()
                    val selectedTujuan = tujuanSpinner.selectedItem.toString()
                    val pasangan = "$selectedAsal - $selectedTujuan"
                    val hargaPasangan = pasanganHarga[pasangan]

                    if (position == 0) {
                        val textView = view as? TextView
                        textView?.setTextColor(resources.getColor(R.color.grey))
                        val hargaNull = 0.0
                        hargaPesanan = hargaDef
                        updateHargaDinamis()
                    } else if (selectedAsal == selectedTujuan) {
                        Toast.makeText(this@FourthActivity, "Ada kesalahan pemesanan, cuy", Toast.LENGTH_SHORT).show()
                        hargaPesanan = hargaDef
                        updateHargaDinamis()
                    } else {
                        val textView = view as? TextView
                        textView?.setTextColor(resources.getColor(android.R.color.black))

                        //Menampilkan kelas_kereta_spinner
                        hidingSpinner.visibility = View.VISIBLE

                        if (hargaPasangan != null){
                            if (position == 1 && kelasKeretaSpinner.selectedItemPosition != 0){
                                hargaPemesanan(hargaPasangan)
                                updateHargaDinamis()
                            }
                            else if (position == 2 && kelasKeretaSpinner.selectedItemPosition != 0){
                                var hargaBisnis = hargaPasangan * 1.5
                                hargaPemesanan(hargaBisnis)
                                updateHargaDinamis()
                            }
                            else if (position == 3 && kelasKeretaSpinner.selectedItemPosition != 0){
                                var hargaEksekutif = hargaPasangan * 2.0
                                hargaPemesanan(hargaEksekutif)
                                updateHargaDinamis()
                            }
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Nothing to do here
                }
            })
            kelasKeretaSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    // Penggabungan item
                    val selectedAsal = asalSpinner.selectedItem.toString()
                    val selectedTujuan = tujuanSpinner.selectedItem.toString()
                    val pasangan = "$selectedAsal - $selectedTujuan"
                    val hargaPasangan = pasanganHarga[pasangan]

                    if (position == 0) {
                        val textView = view as? TextView
                        textView?.setTextColor(resources.getColor(R.color.grey))
                        hargaPesanan = hargaDef
                        updateHargaDinamis()
                    } else {
                        val textView = view as? TextView
                        textView?.setTextColor(resources.getColor(android.R.color.black))

                        if (hargaPasangan != null){
                            if (position == 1){
                                hargaPemesanan(hargaPasangan)
                                updateHargaDinamis()
                            }
                            else if (position == 2){
                                var hargaBisnis = hargaPasangan * 1.5
                                hargaPemesanan(hargaBisnis)
                                updateHargaDinamis()
                            }
                            else{
                                var hargaEksekutif = hargaPasangan * 2.0
                                hargaPemesanan(hargaEksekutif)
                                updateHargaDinamis()
                            }
                        }
                        else {
                            Toast.makeText(this@FourthActivity, "NULL", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Nothing to do here
                }
            })

            // Event ketika ada switch yang berubah
            val switchListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    jumlahSwitchAktif++
//                    Toast.makeText(this@FourthActivity, "${jumlahSwitchAktif}, ${hargaJumlahSwitchAktif}, ${hargaSementara}, ${hargaPesanan}", Toast.LENGTH_SHORT).show()
                } else {
                    jumlahSwitchAktif--
//                    Toast.makeText(this@FourthActivity, "${jumlahSwitchAktif}, ${hargaJumlahSwitchAktif}, ${hargaSementara}, ${hargaPesanan}", Toast.LENGTH_SHORT).show()
                }
                tambahHargaDinamis(jumlahSwitchAktif * 10000.0)
                updateHargaDinamis()
            }

            for (switch in switches) {
                switch.setOnCheckedChangeListener(switchListener)
            }


            bookButton.setOnClickListener {
                //Mengambil nilai
                val selectedTanggal = binding.tanggalRencana.text.toString()
                val selectedAsal = asalSpinner.selectedItem.toString()
                val selectedTujuan = tujuanSpinner.selectedItem.toString()
                val selectedKelas = kelasKeretaSpinner.selectedItem.toString()

                if (selectedTanggal.isEmpty()){
                    val warning = "Tanggal belum ditentukan"
                    Toast.makeText(this@FourthActivity, warning, Toast.LENGTH_SHORT).show()
                }
                else{
                    val intentToThirdActivity =
                        Intent(this@FourthActivity,
                            ThirdActivity::class.java
                        )
                    intentToThirdActivity.putExtra(PLAN_DATE, selectedTanggal)
                    intentToThirdActivity.putExtra(PLAN_FROM, selectedAsal)
                    intentToThirdActivity.putExtra(PLAN_DESTINATION, selectedTujuan)
                    intentToThirdActivity.putExtra(PLAN_CLASS, selectedKelas)
                    startActivity(intentToThirdActivity)
                    finish()
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
                binding.tanggalRencana.text = formattedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
        )
        datePickerDialog.show()
    }
    private fun updateHargaDinamis(){
        var hargaDinamis = hargaPesanan + hargaSementara
        binding.dynamicPrice.text = (format.format(hargaDinamis)).toString()
    }
    private fun hargaPemesanan(harga: Double){
        hargaPesanan = harga
    }
    private fun tambahHargaDinamis(harga: Double){
        hargaSementara = harga
    }
}