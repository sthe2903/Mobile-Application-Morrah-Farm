package com.example.morrahfarmapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.morrahfarmapp.blog.CRUDBlogActivity
import com.example.morrahfarmapp.activities.CRUDProductActivity
import com.example.morrahfarmapp.chat.ChatActivity
import com.example.morrahfarmapp.chat.admin.AdminChatActivity
import com.example.morrahfarmapp.databinding.ActivityAdminBinding
import com.google.firebase.auth.FirebaseAuth

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnLOGOUT.setOnClickListener{
            auth.signOut()
            Intent(this, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
                Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show()
            }
        }

        binding.layoutCRUDProduk.setOnClickListener {
            startActivity(Intent(this, CRUDProductActivity::class.java))
        }

        binding.layoutCRUDBlog.setOnClickListener {
            startActivity(Intent(this, CRUDBlogActivity::class.java))
        }

        binding.layoutAdminChat.setOnClickListener {
            startActivity(Intent(this, AdminChatActivity::class.java))
        }


    }
}

