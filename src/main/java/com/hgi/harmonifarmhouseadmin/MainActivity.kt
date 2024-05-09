package com.hgi.harmonifarmhouseadmin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hgi.harmonifarmhouseadmin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var  binding:ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userRv: RecyclerView
    private lateinit var userAl: ArrayList<CustomData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        userRv = binding.recycleViewMain
        userRv.layoutManager = LinearLayoutManager(this)
        userRv.setHasFixedSize(true)

        userAl = arrayListOf<CustomData>()

        getUserData()


        binding.mainUpload.setOnClickListener{
            val intent = Intent(this@MainActivity, UploadActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.mainUpdate.setOnClickListener{
            val intent = Intent(this@MainActivity, UpdateActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.mainDelete.setOnClickListener{
            val intent = Intent(this@MainActivity, DeleteActivity::class.java)
            startActivity(intent)
            finish()
        }



    }

    private fun getUserData(){

        databaseReference = FirebaseDatabase.getInstance().getReference("Customer Data")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(CustomData::class.java)
                        userAl.add(user!!)

                    }

                    userRv.adapter = CustAdapter(userAl)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}