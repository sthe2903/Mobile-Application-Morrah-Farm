package com.example.morrahfarmapp.chat

data class Message(
    val sender: String = "",
    val recipient: String = "",
    val message: String = "",
    val timestamp: Long = 0
)
