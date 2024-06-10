package com.example.morrahfarmapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.morrahfarmapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.tvToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.RBtnREGISTER.setOnClickListener {
            val email = binding.REdtEmail.text.toString()
            val password = binding.REdtPassword.text.toString()
            val confirmPass = binding.REdtConfirm.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (password == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(this, "Register berhasil!", Toast.LENGTH_SHORT).show()
                                finish()
//                                firebaseAuth.getReference
//                                val intent = Intent(this, LoginActivity::class.java)
//                                startActivity(intent)
//
//                                Toast.makeText(this, "Register berhasil", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Tidak boleh kosong !!", Toast.LENGTH_SHORT).show()

            }
        }


    }

}