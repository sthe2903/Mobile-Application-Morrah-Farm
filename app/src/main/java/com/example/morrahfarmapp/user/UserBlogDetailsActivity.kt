package com.example.morrahfarmapp.user


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.morrahfarmapp.R


class UserBlogDetailsActivity : AppCompatActivity() {
    private lateinit var tvEmpId: TextView
    private lateinit var tvEmpJudul: TextView
    private lateinit var tvEmpIsi: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_blog_details)

        initView()
        setValuesToViews()

    }

    private fun initView() {
        tvEmpId = findViewById(R.id.tvEmpId)
        tvEmpJudul = findViewById(R.id.tvEmpJudul)
        tvEmpIsi = findViewById(R.id.tvEmpIsi)

    }

    private fun setValuesToViews() {
        tvEmpId.text = intent.getStringExtra("empId")
        tvEmpJudul.text = intent.getStringExtra("empJudul")
        tvEmpIsi.text = intent.getStringExtra("empIsi")

    }


}