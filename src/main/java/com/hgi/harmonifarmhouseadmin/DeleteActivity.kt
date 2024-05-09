package com.hgi.harmonifarmhouseadmin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hgi.harmonifarmhouseadmin.databinding.ActivityDeleteBinding
import com.hgi.harmonifarmhouseadmin.databinding.ActivityUpdateBinding

class DeleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteBinding
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDeleteBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.btnScDel.setOnClickListener{
            val fullName = binding.etDel.text.toString()

            if(fullName.isNotEmpty()){
                readData(fullName)
            }else{
                Toast.makeText(this, "Please Masukan Nama !", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDel.setOnClickListener{
            val fullNameDel = binding.etDel.text.toString()
            if (fullNameDel.isNotEmpty()){
                deleteData(fullNameDel)

            }else{
                Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnHomeDel.setOnClickListener{
            val intent = Intent(this@DeleteActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
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
                val status = it.child("statusKav").value
                val ket = "${type.toString()} ${blok.toString()} ${no.toString()}"
                val stat = "Status : ${status.toString()}"



                binding.custTitleDel.text = name.toString()
                binding.ketCustomerDel.text = ket
                binding.statusCustomerDel.text = stat
                Toast.makeText(this, "Find Succes !", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteData(fullName: String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Customer Data")
        databaseReference.child(fullName).removeValue().addOnSuccessListener {
            binding.etDel.text.clear()
            Toast.makeText(this, "Delete !", Toast.LENGTH_SHORT).show()
        }
    }
}