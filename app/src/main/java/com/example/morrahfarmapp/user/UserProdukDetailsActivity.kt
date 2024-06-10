package com.example.morrahfarmapp.user


import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.morrahfarmapp.ProductModel
import com.example.morrahfarmapp.R
import com.example.morrahfarmapp.activities.FetchingActivity
import com.google.firebase.database.FirebaseDatabase


class UserProdukDetailsActivity : AppCompatActivity() {
    private lateinit var tvEmpId: TextView
    private lateinit var tvEmpNamaProduk: TextView
    private lateinit var tvEmpDeskripsi: TextView
    private lateinit var tvEmpHarga: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_produk_details)

        initView()
        setValuesToViews()

    }

    private fun initView() {
        tvEmpId = findViewById(R.id.tvEmpId)
        tvEmpNamaProduk = findViewById(R.id.tvEmpNamaProduk)
        tvEmpDeskripsi = findViewById(R.id.tvEmpDeskripsi)
        tvEmpHarga = findViewById(R.id.tvEmpHarga)

    }

    private fun setValuesToViews() {
        tvEmpId.text = intent.getStringExtra("empId")
        tvEmpNamaProduk.text = intent.getStringExtra("empNamaProduk")
        tvEmpDeskripsi.text = intent.getStringExtra("empDeskripsi")
        tvEmpHarga.text = intent.getStringExtra("empHarga")

    }


}