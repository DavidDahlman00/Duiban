package com.example.duiban.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.duiban.ChatActivity
import com.example.duiban.R
import com.example.duiban.SendFriendRequestActivity
import com.example.duiban.models.DataManager

class AddFriendAdapter: RecyclerView.Adapter<AddFriendAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFriendAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_addfriend_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: AddFriendAdapter.ViewHolder, position: Int) {
        holder.itemName.text = DataManager.usersList2[position].name
        holder.itemImage.setImageResource(R.drawable.oak_tree_silhouette)
    }

    override fun getItemCount(): Int {
        return DataManager.usersList2.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView = itemView.findViewById(R.id.image_addfriend_View)
        var itemName: TextView = itemView.findViewById(R.id.text_addfriend_name)

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, SendFriendRequestActivity::class.java)
                intent.putExtra("friendId", DataManager.usersList2[position].id)
                intent.putExtra("friendName", DataManager.usersList2[position].name)
                itemView.context.startActivity(intent)
            }
        }
    }
}