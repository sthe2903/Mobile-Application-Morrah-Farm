package com.example.morrahfarmapp.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.morrahfarmapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {
    private lateinit var messageInput: EditText
    private lateinit var sendButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var messageList: ArrayList<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Inisialisasi UI
        messageInput = findViewById(R.id.messageInput)
        sendButton = findViewById(R.id.sendButton)
        recyclerView = findViewById(R.id.recyclerView)

        // Inisialisasi RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(messageList)
        recyclerView.adapter = messageAdapter

        // Inisialisasi Firebase
        val firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("messages")

        // Tombol Kirim Pesan
        sendButton.setOnClickListener {
            val sender = FirebaseAuth.getInstance().currentUser?.uid ?: "" // ID pengguna saat ini
            val recipient = "User" // ID admin atau penerima pesan
            val message = messageInput.text.toString().trim()
            val timestamp = System.currentTimeMillis()

            if (message.isNotEmpty()) {
                val newMessage = Message(sender, recipient, message, timestamp)
                databaseReference.push().setValue(newMessage)
                messageInput.text.clear()
            }
        }

        // Baca pesan-pesan dari Firebase
        val messageListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                messageList.clear()
                for (snapshot in dataSnapshot.children) {
                    val message = snapshot.getValue(Message::class.java)
                    if (message != null) {
                        messageList.add(message)
                    }
                }
                messageAdapter.notifyDataSetChanged()
                recyclerView.scrollToPosition(messageList.size - 1)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        }

        databaseReference.addValueEventListener(messageListener)
    }
}