package com.example.duiban.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.duiban.R
import com.example.duiban.models.DataManager

class ChatAdapter(val friendId: String): RecyclerView.Adapter<ChatAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ChatAdapter.ViewHolder, position: Int) {
        if(DataManager.messageList.filter { (it.idFrom == friendId) or (it.idTo == friendId)}[position].nameFrom == friendId){
            holder.itemNameYou.text = ""
            holder.itemNameFriend.text = DataManager.messageList.
            filter { (it.idFrom == friendId) or (it.idTo == friendId)}[position].nameFrom
        }else{
            holder.itemNameYou.text = DataManager.messageList.
            filter { (it.idFrom == friendId) or (it.idTo == friendId)}[position].nameFrom
            holder.itemNameFriend.text = ""
        }
        //holder.itemNameYou.text = DataManager.messageList.filter { (it.idFrom == friendId) or (it.idTo == friendId)}[position].nameFrom

        holder.itemMessage.text = DataManager.messageList.filter { (it.idFrom == friendId) or (it.idTo == friendId)}[position].message

    }

    override fun getItemCount(): Int {
        return DataManager.messageList.filter { (it.idFrom == friendId) or
                (it.idTo == friendId)}.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemNameYou: TextView = itemView.findViewById(R.id.message_sender1)
        var itemNameFriend: TextView = itemView.findViewById(R.id.message_sender2)
        var itemMessage: TextView = itemView.findViewById(R.id.message_text)

    }
}