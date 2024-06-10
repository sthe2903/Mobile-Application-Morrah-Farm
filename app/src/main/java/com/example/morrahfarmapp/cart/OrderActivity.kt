package com.example.morrahfarmapp.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.morrahfarmapp.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OrderActivity : AppCompatActivity() {
    private lateinit var btnOrderPesanan: Button

    private lateinit var empRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var empList: ArrayList<CartModel>
    private lateinit var dbRef: DatabaseReference

//    private lateinit var textViewOrder: TextView
//

    private lateinit var orderHistoryRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        empRecyclerView = findViewById(R.id.rvEmp)
        empRecyclerView.layoutManager = LinearLayoutManager(this)
        empRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        empList = arrayListOf<CartModel>()

        getEmployeesData()

        orderHistoryRef = FirebaseDatabase.getInstance().getReference("Cart")

        btnOrderPesanan = findViewById(R.id.btnOrderPesanan)

        btnOrderPesanan.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }


    }



//    private fun saveToOrderHistory(orderText: String) {
//        orderHistoryRef = FirebaseDatabase.getInstance().getReference("order_history")
//
//        val orderId = orderHistoryRef.push().key!!
//        val timestamp = System.currentTimeMillis()
//
//        val order = OrderModel(orderId, orderText, timestamp)
//        orderHistoryRef.child(orderId).setValue(order)
//            .addOnSuccessListener {
//                println("Data berhasil disimpan ke riwayat pesanan di Firebase")
//            }
//            .addOnFailureListener {
//                println("Terjadi kesalahan saat menyimpan data ke riwayat pesanan di Firebase")
//            }
//    }

    private fun getEmployeesData() {

        empRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Cart")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(CartModel::class.java)
                        empList.add(empData!!)
                    }
                    val mAdapter = OrderAdapter(empList)
                    empRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : OrderAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@OrderActivity, OrderDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("Id", empList[position].cartId)
                            intent.putExtra("NamaProduk", empList[position].cartNamaProduk)
                            intent.putExtra("Jumlah", empList[position].cartJumlah)
                            startActivity(intent)
                        }

                    })

                    empRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}