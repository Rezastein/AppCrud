package com.hgi.harmonifarmhouseadmin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hgi.harmonifarmhouseadmin.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSc.setOnClickListener{
            val searchV = binding.etSc.text.toString()
            if (searchV.isNotEmpty()){
                readData(searchV)
            }else{
                Toast.makeText(this, "Masukan Nama !", Toast.LENGTH_SHORT).show()

            }
        }
        binding.btnHome.setOnClickListener{
            val intent = Intent(this@UpdateActivity, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

        binding.btnUP.setOnClickListener{
            val searchV = binding.etSc.text.toString()
            if(searchV.isNotEmpty()){
                readData(searchV)
                databaseReference = FirebaseDatabase.getInstance().getReference("Customer Data")
                databaseReference.child(searchV).get().addOnSuccessListener{

                    if (it.exists()){
                        val newPrice = binding.inPriceNew
                        val inTotalPrice = it.child("totalPrice").value

                        val totalPrice = inTotalPrice.toString().toInt() - newPrice.text.toString().toInt()
                        binding.priceEndNew.text = totalPrice.toString()

                    }


                    val totalPrice = binding.priceEndNew.text.toString()
                    updateData(searchV, totalPrice)


                }


            }


        }



    }

    private fun readData(fullName: String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Customer Data")
        databaseReference.child(fullName).get().addOnSuccessListener {


            if (it.exists()){
                val name = it.child("fullName").value
                val type = it.child("typeKav").value
                val blok = it.child("blokKav").value
                val no = it.child("noKav").value
                val totalPrice = it.child("totalPrice").value
                val status = it.child("statusKav").value
                val ket = "${type.toString()} ${blok.toString()} ${no.toString()}"
                val stat = "Status : ${status.toString()}"



                binding.custTitle.text = name.toString()
                binding.ketCustomer.text = ket
                binding.statusCustomer.text = stat
                binding.priceEndNew.text = totalPrice.toString()
                Toast.makeText(this, "Find !", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateData(fullName: String, totalPrice: String){
        val CustomerData = mapOf<String, String>("totalPrice" to totalPrice)
        databaseReference.child(fullName).updateChildren(CustomerData).addOnSuccessListener {
            binding.inPriceNew.text.clear()

        }
    }
}