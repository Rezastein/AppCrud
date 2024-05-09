package com.hgi.harmonifarmhouseadmin

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hgi.harmonifarmhouseadmin.databinding.ActivityUploadBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UploadActivity : AppCompatActivity() {
   private lateinit var binding: ActivityUploadBinding
   private lateinit var databaseReferance: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        Date Picker
        val myCal = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
            myCal.set(Calendar.YEAR, year)
            myCal.set(Calendar.MONTH, month)
            myCal.set(Calendar.DAY_OF_MONTH, dayOfMonth)


        }
//        datePicker in
        binding.etDate.setOnClickListener{
            updateLable(myCal)
            DatePickerDialog(this, datePicker, myCal.get(Calendar.YEAR), myCal.get(Calendar.MONTH), myCal.get(Calendar.DAY_OF_MONTH)).show()
        }
//        datePicker Out
        binding.etOutDate.setOnClickListener{
            updateLable2(myCal)
            DatePickerDialog(this, datePicker, myCal.get(Calendar.YEAR), myCal.get(Calendar.MONTH), myCal.get(Calendar.DAY_OF_MONTH)).show()
        }
        binding.btnHomeMain.setOnClickListener{
            val intent = Intent(this@UploadActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

//        AutoCompleteTextView
        val typeAr = resources.getStringArray(R.array.type_array)
        val blokAr = resources.getStringArray(R.array.blok_array)
        val statAr = resources.getStringArray(R.array.status_array)
        val noAr = resources.getStringArray(R.array.no_array)
        val arrayTypeAdapter = ArrayAdapter(this, R.layout.dropdown_item, typeAr)
        val arrayBlokAdapter = ArrayAdapter(this, R.layout.dropdown_item, blokAr)
        val arrayStatAdapter = ArrayAdapter(this, R.layout.dropdown_item, statAr)
        val arrayNoAdapter = ArrayAdapter(this, R.layout.dropdown_item, noAr)
        binding.actv1.setAdapter(arrayTypeAdapter)
        binding.actv2.setAdapter(arrayBlokAdapter)
        binding.actv4.setAdapter(arrayStatAdapter)
        binding.actv3.setAdapter(arrayNoAdapter)


        val totalPrice = binding.priceDisplay.text.toString()

        binding.btnTtlPrice.setOnClickListener{

            if(totalPrice.isNotEmpty()){
                priceUp(totalPrice)
            } else {
                Toast.makeText(this, "Please Select Type !", Toast.LENGTH_SHORT).show()
            }

        }






//        Add Data

        binding.btnAdd.setOnClickListener{
            val name = binding.fullName.text.toString()
            val date_in = binding.etDate.text.toString()
            val type = binding.actv1.text.toString()
            val status = binding.actv4.text.toString()
            val blok = binding.actv2.text.toString()
            val no = binding.actv3.text.toString()
            val measure = binding.ukuran.text.toString()
            val in_price = binding.inPrice.text.toString()
            val disc = binding.disc.text.toString()
            val age = binding.age.text.toString()
            val no_phone = binding.noPhone.text.toString()
            val address = binding.address.text.toString()
            val agent = binding.agent.text.toString()
            val date_out = binding.etOutDate.text.toString()

            val totalPrice = binding.priceDisplay.text.toString()


            val idCust = "$type $blok $no"

//            val totalPrice =  totalAll.toInt() - in_price.toInt() - disc.toInt()









//            by name
            databaseReferance = FirebaseDatabase.getInstance().getReference("Customer Data")
            val CustomerData = CustomData(name, date_in, type, status, blok, no, measure, in_price,totalPrice, disc, age,no_phone,address,agent,date_out )
            databaseReferance.child(name).setValue(CustomerData).addOnSuccessListener {
                binding.fullName.text.clear()
                binding.etDate.text?.clear()
                binding.ukuran.text.clear()
                binding.inPrice.text.clear()
                binding.disc.text.clear()
                binding.age.text.clear()
                binding.noPhone.text.clear()
                binding.address.text.clear()
                binding.agent.text.clear()
                binding.etOutDate.text?.clear()


                Toast.makeText(this, "Tersimpan !", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@UploadActivity, MainActivity::class.java)
                startActivity(intent)
                finish()



            }.addOnFailureListener{
                Toast.makeText(this, "Gagal Input Data !", Toast.LENGTH_SHORT).show()
            }
//            by id

            databaseReferance = FirebaseDatabase.getInstance().getReference("Customer Data ID")
            val CustomerDataID = CustomDataID(name, date_in, type, status, blok, no, measure, in_price,totalPrice, disc, age,no_phone,address,agent,date_out )
            databaseReferance.child(idCust).setValue(CustomerDataID).addOnSuccessListener {
                binding.fullName.text.clear()
                binding.etDate.text?.clear()
                binding.ukuran.text.clear()
                binding.inPrice.text.clear()

                binding.disc.text.clear()
                binding.age.text.clear()
                binding.noPhone.text.clear()
                binding.address.text.clear()
                binding.agent.text.clear()
                binding.etOutDate.text?.clear()


                Toast.makeText(this, "Tersimpan !", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@UploadActivity, MainActivity::class.java)
                startActivity(intent)
                finish()



            }.addOnFailureListener{
                Toast.makeText(this, "Gagal Input Data !", Toast.LENGTH_SHORT).show()
            }
        }


    }
//    function datePicker

    private fun updateLable(myCal : Calendar){
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.ROOT)
        binding.etDate.setText(sdf.format(myCal.time))
    }
    private fun updateLable2(myCal : Calendar){
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.ROOT)
        binding.etOutDate.setText(sdf.format(myCal.time))
    }

    private fun priceUp(totalPrice : String) {
//        Progres Price

//            variable data harga
        val za = 276500000
        val ru = 68500000
        val sa = 67000000
        val km = 64000000
        val kmp = 69500000
        val tp = 49500000
        val am = 45500000
        val dm = 85000000
        val ci = 82000000
        val ja = 72500000
        val zi = 69500000
//            variable kelebihan
        val zak = 475000
        val ruk = 580000
        val sak = 640000
        val kmk = 610000
        val kmpk = 520000
        val tpk = 470000
        val amk = 430000
        val dmk = 725000
        val cik = 695000
        val jak = 695000
        val zik = 665000

        val measure = binding.ukuran.text.toString().toInt()
        val type = binding.actv1.text.toString()
        val inPrice = binding.inPrice.text.toString().toInt()
        val disc = binding.disc.text.toString().toInt()

        if (type == "Zamrud") {
            val measFinal = measure - 500
            val priceKel = measFinal * zak
            val priceOut = priceKel + za

            val ttlPrice = priceOut - inPrice - disc
            binding.priceDisplay.text = ttlPrice.toString()


        } else if (type == "Ruby") {
            val measFinal = measure - 100
            val priceKel = measFinal * ruk
            val priceOut = priceKel + ru

            val ttlPrice = priceOut - inPrice - disc
            binding.priceDisplay.text = ttlPrice.toString()

        } else if (type == "Sapphire") {
            val measFinal = measure - 100
            val priceKel = measFinal * sak
            val priceOut = priceKel + sa

            val ttlPrice = priceOut - inPrice - disc
            binding.priceDisplay.text = ttlPrice.toString()

        } else if (type == "Kalimaya") {
            val measFinal = measure - 100
            val priceKel = measFinal * kmk
            val priceOut = priceKel + km

            val ttlPrice = priceOut - inPrice - disc
            binding.priceDisplay.text = ttlPrice.toString()

        }

    }



}