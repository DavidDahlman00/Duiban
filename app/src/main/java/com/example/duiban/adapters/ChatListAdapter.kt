package com.example.duiban.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
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
import com.example.duiban.models.UserClass
import java.lang.NullPointerException

class ChatListAdapter: RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {
    private lateinit var localFriendList: List<UserClass>
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatListAdapter.ViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.item_chatlist, parent, false)


        return ViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ChatListAdapter.ViewHolder, position: Int) {

         localFriendList = DataManager.friendsList.filter { it ->
            val userIt = it
            DataManager.friendsList2.any { userIt.id == it.id }
        }
        Log.d("!!!FriendList", localFriendList.toString())
        holder.itemName.text = localFriendList[position].name
        var message = ""
        if(DataManager.messageList.filter { (it.idFrom == localFriendList[position].id) or
                   (it.idTo == localFriendList[position].id)}.size > 0){
                       try {
                           message = DataManager.messageList.filter { (it.idFrom == localFriendList[position].id) or
                                   (it.idTo == localFriendList[position].id) }.sortedByDescending { it.time }[0].message
                       }catch (e: NullPointerException){
                           message = ""
                       }

       }

        holder.itemMessage.text = message

    }

    override fun getItemCount(): Int {
        return DataManager.friendsList2.size    // can't use localFriendList because it isn't initialised yet.
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemName: TextView = itemView.findViewById(R.id.contactTextName)
        var itemMessage: TextView = itemView.findViewById(R.id.contactTextMessage)

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ChatActivity::class.java)
                intent.putExtra("friendId", localFriendList[position].id)
                intent.putExtra("friendName", localFriendList[position].name)
                itemView.context.startActivity(intent)
            }
        }
    }
}