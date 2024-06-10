package com.example.morrahfarmapp.activities

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
import com.google.firebase.database.FirebaseDatabase

class EmployeeDetailsActivity : AppCompatActivity() {
    private lateinit var tvEmpId: TextView
    private lateinit var tvEmpNamaProduk: TextView
    private lateinit var tvEmpDeskripsi: TextView
    private lateinit var tvEmpHarga: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empNamaProduk").toString(),

            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("empId").toString()
            )
        }

    }

    private fun initView() {
        tvEmpId = findViewById(R.id.tvEmpId)
        tvEmpNamaProduk = findViewById(R.id.tvEmpNamaProduk)
        tvEmpDeskripsi = findViewById(R.id.tvEmpDeskripsi)
        tvEmpHarga = findViewById(R.id.tvEmpHarga)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvEmpId.text = intent.getStringExtra("empId")
        tvEmpNamaProduk.text = intent.getStringExtra("empNamaProduk")
        tvEmpDeskripsi.text = intent.getStringExtra("empDeskripsi")
        tvEmpHarga.text = intent.getStringExtra("empHarga")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Produk").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Data Produk Terhapus", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        empId: String,
        empNamaProduk: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etEmpNamaProduk = mDialogView.findViewById<EditText>(R.id.etEmpNamaProduk)
        val etEmpDeskripsi = mDialogView.findViewById<EditText>(R.id.etEmpDeskripsi)
        val etEmpHarga = mDialogView.findViewById<EditText>(R.id.etEmpHarga)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etEmpNamaProduk.setText(intent.getStringExtra("empNamaProduk").toString())
        etEmpDeskripsi.setText(intent.getStringExtra("empDeskripsi").toString())
        etEmpHarga.setText(intent.getStringExtra("empHarga").toString())

        mDialog.setTitle("Updating $empNamaProduk Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                empId,
                etEmpNamaProduk.text.toString(),
                etEmpDeskripsi.text.toString(),
                etEmpHarga.text.toString()
            )

            Toast.makeText(applicationContext, "Data Produk Terupdate", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvEmpNamaProduk.text = etEmpNamaProduk.text.toString()
            tvEmpDeskripsi.text = etEmpDeskripsi.text.toString()
            tvEmpHarga.text = etEmpHarga.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        namaProduk: String,
        deskripsi: String,
        harga: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Produk").child(id)
        val empInfo = ProductModel(id, namaProduk, deskripsi, harga)
        dbRef.setValue(empInfo)
    }

}