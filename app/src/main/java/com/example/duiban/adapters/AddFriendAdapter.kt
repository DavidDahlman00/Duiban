package com.example.duiban.adapters

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
import com.example.duiban.SendFriendRequestActivity
import com.example.duiban.models.DataManager
import com.example.duiban.models.ProfileImageRefClass
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*

class AddFriendAdapter: RecyclerView.Adapter<AddFriendAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFriendAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_addfriend_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: AddFriendAdapter.ViewHolder, position: Int) {

        when {
            DataManager.profileImageReference.any { it.id == DataManager.usersList2[position].id } -> {
                val imageRef =  DataManager.profileImageReference
                    .filter { it.id == DataManager.usersList2[position].id}[0]
                    .image
                Log.d("!!!!!!", imageRef)
                Log.d("!!!!!!", DataManager.usersList2[position].name)
                Picasso.get().load(imageRef).into(holder.itemImage)
            }
            DataManager.usersList2[position].profileImage == "" -> {
                holder.itemImage.setImageResource(R.drawable.ic_name_person)

            }
            else -> {
                Picasso.get().load(DataManager.usersList2[position].profileImage).into(holder.itemImage)
                val imageRef = ProfileImageRefClass(DataManager.usersList2[position].id, DataManager.usersList2[position].profileImage)
                DataManager.profileImageReference.add(imageRef)
            }
        }
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
                intent.putExtra("profileImage", DataManager.usersList2[position].profileImage)
                itemView.context.startActivity(intent)
            }
        }
    }
}