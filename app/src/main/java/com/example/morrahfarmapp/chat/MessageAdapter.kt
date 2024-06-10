package com.example.morrahfarmapp.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.morrahfarmapp.R

class MessageAdapter (private val messageList: ArrayList<Message>) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val senderTextView: TextView = itemView.findViewById(R.id.senderTextView)
        val recipientTextView: TextView = itemView.findViewById(R.id.recipientTextView)
        val messageTextView: TextView = itemView.findViewById(R.id.messageTextView)
        val timestampTextView: TextView = itemView.findViewById(R.id.timestampTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messageList[position]
//        holder.senderTextView.text = message.sender
        holder.recipientTextView.text = message.recipient
        holder.messageTextView.text = message.message
        holder.timestampTextView.text = message.timestamp.toString()
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
}