package com.example.duiban.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.duiban.R
import com.example.duiban.models.DataManager

class AddFriendAdapter: RecyclerView.Adapter<AddFriendAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFriendAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_addfriend_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: AddFriendAdapter.ViewHolder, position: Int) {
        holder.itemName.text = "contact position $position"
        holder.itemImage.setImageResource(R.drawable.oak_tree_silhouette)
    }

    override fun getItemCount(): Int {
        return DataManager.usersList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView = itemView.findViewById(R.id.image_addfriend_View)
        var itemName: TextView = itemView.findViewById(R.id.text_addfriend_name)
    }
}