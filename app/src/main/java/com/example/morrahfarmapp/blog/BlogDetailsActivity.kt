package com.example.morrahfarmapp.blog

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.morrahfarmapp.R
import com.example.morrahfarmapp.activities.FetchingActivity
import com.google.firebase.database.FirebaseDatabase

class BlogDetailsActivity : AppCompatActivity() {
    private lateinit var tvEmpId: TextView
    private lateinit var tvEmpJudul: TextView
    private lateinit var tvEmpIsi: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empJudul").toString()

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
        tvEmpJudul = findViewById(R.id.tvEmpJudul)
        tvEmpIsi = findViewById(R.id.tvEmpIsi)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvEmpId.text = intent.getStringExtra("empId")
        tvEmpJudul.text = intent.getStringExtra("empJudul")
        tvEmpIsi.text = intent.getStringExtra("empIsi")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Blog").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Data Blog Terhapus", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        empId: String,
        empJudul: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.blogupdate_dialog, null)

        mDialog.setView(mDialogView)

        val etEmpJudul = mDialogView.findViewById<EditText>(R.id.etEmpJudul)
        val etEmpIsi = mDialogView.findViewById<EditText>(R.id.etEmpIsi)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etEmpJudul.setText(intent.getStringExtra("empJudul").toString())
        etEmpIsi.setText(intent.getStringExtra("empIsi").toString())

        mDialog.setTitle("Updating $empJudul Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                empId,
                etEmpJudul.text.toString(),
                etEmpIsi.text.toString(),
            )

            Toast.makeText(applicationContext, "Data Blog Terupdate", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvEmpJudul.text = etEmpJudul.text.toString()
            tvEmpIsi.text = etEmpIsi.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        judul: String,
        isi: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Blog").child(id)
        val empInfo = BlogModel(id, judul, isi)
        dbRef.setValue(empInfo)
    }

}