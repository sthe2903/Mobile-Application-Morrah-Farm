package com.example.morrahfarmapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.morrahfarmapp.ProductModel
import com.example.morrahfarmapp.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {
    private lateinit var etNamaProduk: EditText
    private lateinit var etDeskripsi: EditText
    private lateinit var etHarga: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etNamaProduk = findViewById(R.id.etNamaProduk)
        etDeskripsi = findViewById(R.id.etDeskripsi)
        etHarga = findViewById(R.id.etHarga)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Produk")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {

        //getting values
        val empNamaProduk = etNamaProduk.text.toString()
        val empDeskripsi = etDeskripsi.text.toString()
        val empHarga = etHarga.text.toString()

        if (empNamaProduk.isEmpty()) {
            etNamaProduk.error = "Masukkan Nama Produk"
        }
        if (empDeskripsi.isEmpty()) {
            etDeskripsi.error = "Masukkan Deskripsi"
        }
        if (empHarga.isEmpty()) {
            etHarga.error = "Masukkan Harga"
        }

        val empId = dbRef.push().key!!

        val employee = ProductModel(empId, empNamaProduk, empDeskripsi, empHarga)

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etNamaProduk.text.clear()
                etDeskripsi.text.clear()
                etHarga.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}