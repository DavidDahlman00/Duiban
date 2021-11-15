package com.example.duiban.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.duiban.R

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
        holder.itemName.text = "Sven"
        holder.itemMessage.text = "Sven has accepted you as friend"

    }

    override fun getItemCount(): Int {
        return 3
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView = itemView.findViewById(R.id.contactImage)
        var itemName: TextView = itemView.findViewById(R.id.contactTextName)
        var itemMessage: TextView = itemView.findViewById(R.id.contactTextMessage)
    }
}