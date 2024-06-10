package com.example.morrahfarmapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.morrahfarmapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Username validation
        val usernameStream = RxTextView.textChanges(binding.LEdtEmail)
            .skipInitialValue()
            .map { username ->
                username.length < 5
            }
        usernameStream.subscribe {
            showTextMinimalAlert(it, "Username")
        }

        // Password Validation
        val passwordStream = RxTextView.textChanges(binding.LEdtConfirm)
            .skipInitialValue()
            .map { password ->
                password.length < 5
            }
        passwordStream.subscribe {
            showTextMinimalAlert(it, "Password")
        }

        // Button Enable True or False
        val invalidFieldStream = Observable.combineLatest(
            usernameStream,
            passwordStream
        ) { usernameInvalid: Boolean, passwordInvalid: Boolean ->
            !usernameInvalid && !passwordInvalid
        }
        invalidFieldStream.subscribe { isValid ->
            if (isValid) {
                binding.LBtnLOGIN.isEnabled = true
                binding.LBtnLOGIN.backgroundTintList = ContextCompat.getColorStateList(this, R.color.primaryColor)
            } else {
                binding.LBtnLOGIN.isEnabled = false
                binding.LBtnLOGIN.backgroundTintList = ContextCompat.getColorStateList(this, R.color.darker_grey)
            }
        }

        // Click
        binding.LBtnLOGIN.setOnClickListener {
            val email = binding.LEdtEmail.text.toString().trim()
            val password = binding.LEdtConfirm.text.toString()
            loginUser(email, password)
        }
        binding.tvToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

}

    private fun showTextMinimalAlert(isNotValid: Boolean, text: String) {
        if (text == "Username")
            binding.LEdtEmail.error = if (isNotValid) "$text harus lebih dari 5 huruf" else null
        else if (text == "Password")
            binding.LEdtConfirm.error = if (isNotValid) "$text harus lebih dari 5 huruf" else null
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { login ->
                if (login.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        checkUserRole(userId)
                    }
                } else {
                    Toast.makeText(this, "Login gagal, Silahkan coba lagi!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkUserRole(userId: String) {
        val usersRef = FirebaseDatabase.getInstance().reference.child("users")
        usersRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val role = dataSnapshot.child("role").getValue(String::class.java)
                    if (role == "admin") {
                        // Redirect to admin activity
                        Intent(this@LoginActivity, AdminActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(this)
                        }
                    } else if (role == "user") {
                        // Redirect to user activity
                        Intent(this@LoginActivity, HomeActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(this)
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Invalid user role", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "User data not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@LoginActivity, "Database error", Toast.LENGTH_SHORT).show()
            }
        })
    }


}