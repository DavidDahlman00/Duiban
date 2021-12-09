package com.example.duiban.adapters

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.duiban.R
import com.example.duiban.models.DataManager


class ChatAdapter(val friendId: String): RecyclerView.Adapter<ChatAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ChatAdapter.ViewHolder, position: Int) {

        if(DataManager.messageList.filter { (it.idFrom == friendId) or (it.idTo == friendId)}.sortedBy { it.time } [position].nameFrom == friendId){
            holder.itemNameYou.text = ""
            holder.itemNameFriend.text = DataManager.messageList.
            filter { (it.idFrom == friendId) or (it.idTo == friendId)}.sortedBy{ it.time } [position].nameFrom
        }else{
            holder.itemNameYou.text = DataManager.messageList.
            filter { (it.idFrom == friendId) or (it.idTo == friendId)}.sortedBy { it.time } [position].nameFrom
            holder.itemNameFriend.text = ""
        }
        //holder.itemNameYou.text = DataManager.messageList.filter { (it.idFrom == friendId) or (it.idTo == friendId)}[position].nameFrom
    val thisMessage = DataManager.messageList.filter { (it.idFrom == friendId) or (it.idTo == friendId)}.sortedBy { it.time } [position]
        holder.itemMessage.text = thisMessage.message

        holder.itemTime.text = thisMessage.getDateTime(thisMessage.time)
    }

    override fun getItemCount(): Int {
        return DataManager.messageList.filter { (it.idFrom == friendId) or
                (it.idTo == friendId)}.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var itemNameYou: TextView = itemView.findViewById(R.id.message_sender1)
        var itemNameFriend: TextView = itemView.findViewById(R.id.message_sender2)
        var itemMessage: TextView = itemView.findViewById(R.id.message_text)
        var itemTime: TextView = itemView.findViewById(R.id.message_time)

        init {
            itemView.setOnClickListener {
                var clipboard = itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("textCopy",itemMessage.text)
                clipboard.setPrimaryClip(clip)
            }
        }
    }
}


