package com.example.duiban.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.duiban.ChatActivity
import com.example.duiban.R
import com.example.duiban.models.DataManager
import com.example.duiban.models.MessageClass
import java.lang.NullPointerException

class ChatListAdapter: RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {
   private  var latestMessageList = mutableListOf<MessageClass>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatListAdapter.ViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.item_chatlist, parent, false)

     /*   for (index in 0..DataManager.friendsList.size-1){

        }*/

        return ViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ChatListAdapter.ViewHolder, position: Int) {



        holder.itemName.text = DataManager.friendsList[position].name
        var message = ""
        if(DataManager.messageList.filter { (it.idFrom == DataManager.friendsList[position].id) or
                   (it.idTo == DataManager.friendsList[position].id)}.size > 0){
                       try {
                           message = DataManager.messageList.filter { (it.idFrom == DataManager.friendsList[position].id) or
                                   (it.idTo == DataManager.friendsList[position].id) }.sortedByDescending { it.time }[0].message
                       }catch (e: NullPointerException){
                           message = ""
                       }

       }

        holder.itemMessage.text = message

    }

    override fun getItemCount(): Int {
        return DataManager.friendsList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemName: TextView = itemView.findViewById(R.id.contactTextName)
        var itemMessage: TextView = itemView.findViewById(R.id.contactTextMessage)

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ChatActivity::class.java)
                intent.putExtra("friendId", DataManager.friendsList[position].id)
                intent.putExtra("friendName", DataManager.friendsList[position].name)
                itemView.context.startActivity(intent)
            }
        }
    }
}