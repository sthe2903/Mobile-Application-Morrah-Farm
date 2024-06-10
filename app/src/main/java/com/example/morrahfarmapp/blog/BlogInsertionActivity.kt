package com.example.morrahfarmapp.blog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.morrahfarmapp.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class BlogInsertionActivity : AppCompatActivity() {
    private lateinit var etJudul: EditText
    private lateinit var etIsi: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog_insertion)

        etJudul = findViewById(R.id.etJudul)
        etIsi = findViewById(R.id.etIsi)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Blog")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {

        //getting values
        val empJudul = etJudul.text.toString()
        val empIsi = etIsi.text.toString()

        if (empJudul.isEmpty()) {
            etJudul.error = "Masukkan Judul"
        }
        if (empIsi.isEmpty()) {
            etIsi.error = "Masukkan Isi"
        }
        val empId = dbRef.push().key!!

        val employee = BlogModel(empId, empJudul, empIsi)

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etJudul.text.clear()
                etIsi.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}