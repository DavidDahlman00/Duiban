package com.example.duiban.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.duiban.R
import com.example.duiban.models.DataManager

class ContactsAdapter: RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ContactsAdapter.ViewHolder, position: Int) {
        holder.itemName.text = DataManager.friendsList2[position].name
        holder.itemImage.setImageResource(R.drawable.oak_tree_silhouette)

        if (DataManager.friendsList2[position].accepted_me){
            holder.itemAcceptedStatus.text = "accepted"
            if (DataManager.friendsList2[position].accepted_contact){
                holder.itemAcceptedButton.visibility = View.INVISIBLE
            }else{
                holder.itemAcceptedButton.visibility = View.VISIBLE
            }
        }else{
            holder.itemAcceptedStatus.text = "not accepted"
        }
    }

    override fun getItemCount(): Int {
        return DataManager.friendsList2.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView = itemView.findViewById(R.id.imageContact)
        var itemName: TextView = itemView.findViewById(R.id.textNameContactList)
        var itemAcceptedStatus: TextView = itemView.findViewById(R.id.accepted_status_text)
        var itemAcceptedButton: TextView = itemView.findViewById(R.id.accept_button)
    }

}