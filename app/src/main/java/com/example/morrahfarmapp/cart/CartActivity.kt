package com.example.morrahfarmapp.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.morrahfarmapp.ProductModel
import com.example.morrahfarmapp.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CartActivity : AppCompatActivity() {
    private lateinit var etNamaProduk: EditText
    private lateinit var etJumlah: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        etNamaProduk = findViewById(R.id.etNamaProduk)
        etJumlah = findViewById(R.id.etJumlah)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Cart")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }

    }

    private fun saveEmployeeData() {

        //getting values
        val cartNamaProduk = etNamaProduk.text.toString()
        val cartJumlah = etJumlah.text.toString()

        if (cartNamaProduk.isEmpty()) {
            etNamaProduk.error = "Masukkan Nama Produk"
        }
        if (cartJumlah.isEmpty()) {
            etJumlah.error = "Masukkan Jumlah"
        }


        val cartId = dbRef.push().key!!

        val employee = CartModel(cartId, cartNamaProduk, cartJumlah)

        dbRef.child(cartId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Produk telah ditambahkan ke keranjang anda!", Toast.LENGTH_LONG).show()

                etNamaProduk.text.clear()
                etJumlah.text.clear()



            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }
}