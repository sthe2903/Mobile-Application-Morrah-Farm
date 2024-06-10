package com.example.morrahfarmapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.morrahfarmapp.cart.OrderActivity
import com.example.morrahfarmapp.chat.ChatActivity
import com.example.morrahfarmapp.databinding.ActivityHomeBinding
import com.example.morrahfarmapp.user.UserBlogActivity
import com.example.morrahfarmapp.user.UserProductActivity
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
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

        binding.layoutProduk.setOnClickListener {
            startActivity(Intent(this, UserProductActivity::class.java))
        }

        binding.layoutBlog.setOnClickListener {
            startActivity(Intent(this, UserBlogActivity::class.java))
        }

        binding.layoutCart.setOnClickListener {
            startActivity(Intent(this, OrderActivity::class.java))
        }

        binding.layoutChat.setOnClickListener {
            startActivity(Intent(this, ChatActivity::class.java))
        }



    }

//        loadFragment(DashboardFragment())
//        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
//        bottomNav.setOnClickListener {
//            when (it.id) {
//                R.id.dashboard -> {
//                    loadFragment(DashboardFragment())
//                    true
//                }
//                R.id.product -> {
//                    loadFragment(ProdukFragment())
//                    true
//                }
//                R.id.blog -> {
//                    loadFragment(BlogFragment())
//                    true
//                }
//                R.id.about -> {
//                    loadFragment(AboutFragment())
//                    true
//                }
//                R.id.contact -> {
//                    loadFragment(ContactFragment())
//                    true
//                }
//            }
//        }
//        supportActionBar?.hide()
//
//        auth = FirebaseAuth.getInstance()
//
//        binding.btnLOGOUT.setOnClickListener {
//            auth.signOut()
//            Intent(this, LoginActivity::class.java).also {
//                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(it)
//                Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show()
//            }
//        }


//    }
//
//    private fun loadFragment(fragment: Fragment) {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.container,fragment)
//        transaction.commit()
//    }
}