package com.example.duiban.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.duiban.ChatActivity
import com.example.duiban.MainActivity
import com.example.duiban.R
import com.example.duiban.models.DataManager

class ContactListAdapter: RecyclerView.Adapter<ContactListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactListAdapter.ViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ContactListAdapter.ViewHolder, position: Int) {
        holder.itemImage.setImageResource(R.drawable.ic_launcher_foreground)
        holder.itemName.text = DataManager.usersList[position].name
        holder.itemMessage.text = "Sven has accepted you as friend"

    }

    override fun getItemCount(): Int {
        return DataManager.usersList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView = itemView.findViewById(R.id.contactImage)
        var itemName: TextView = itemView.findViewById(R.id.contactTextName)

        var itemMessage: TextView = itemView.findViewById(R.id.contactTextMessage)

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ChatActivity::class.java)
                intent.putExtra("friendId", DataManager.usersList[position].id)
                intent.putExtra("friendName", DataManager.usersList[position].name)
                itemView.context.startActivity(intent)
            }
        }
    }
}