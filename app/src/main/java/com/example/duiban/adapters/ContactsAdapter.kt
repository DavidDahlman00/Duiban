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
import com.example.duiban.models.DataManager
import com.example.duiban.models.FriendClass
import com.example.duiban.models.ProfileImageRefClass
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_send_friend_request.*

class ContactsAdapter: RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private var db = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ContactsAdapter.ViewHolder, position: Int) {
        holder.friendName = DataManager.friendsList2[position].name
        holder.friendId = DataManager.friendsList2[position].id
        holder.itemName.text = DataManager.friendsList2[position].name


        val imageRef = DataManager.usersList.filter { it.id == DataManager.friendsList2[position].id}[0].profileImage
        if (imageRef == ""){
            holder.itemImage.setImageResource(R.drawable.ic_name_person)
        }else{
            Picasso.get().load(imageRef).into(holder.itemImage)
        }

        holder.itemtesttext.text = DataManager.friendsList2[position].accepted_me.toString()

        holder.itemAcceptedStatus.text = DataManager.friendsList2[position].accepted_me.toString()

        if(DataManager.friendsList2[position].accepted_me && DataManager.friendsList2[position].accepted_contact){
            holder.friendRequsetAccepted = true
        }else{
            holder.friendRequsetAccepted = false
        }

        if (DataManager.friendsList2[position].accepted_me){
            holder.itemAcceptedButton.setVisibility(View.INVISIBLE);
            holder.itemDeclineButton.setVisibility(View.INVISIBLE);
            if(DataManager.friendsList2[position].accepted_contact){
                holder.itemAcceptedStatus.text = ""
            }else{
                holder.itemAcceptedStatus.text = "Wating for ${DataManager.friendsList2[position].name} to accept your request"
            }
        }else{
            holder.itemAcceptedButton.setVisibility(View.VISIBLE);
            holder.itemDeclineButton.setVisibility(View.VISIBLE);
            holder.itemAcceptedStatus.text = "${DataManager.friendsList2[position].name} would like to add you as friend"
        }


    }

    override fun getItemCount(): Int {
        return DataManager.friendsList2.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var friendName: String = ""
        var friendId: String = ""
        var friendRequsetAccepted: Boolean = false
        var itemtesttext: TextView = itemView.findViewById(R.id.textViewtest)
        var itemImage: ImageView = itemView.findViewById(R.id.imageContact)
        var itemName: TextView = itemView.findViewById(R.id.textNameContactList)
        var itemAcceptedStatus: TextView = itemView.findViewById(R.id.accepted_status_text)
        var itemAcceptedButton: TextView = itemView.findViewById(R.id.accept_friend_request_button)
        var itemDeclineButton: TextView = itemView.findViewById(R.id.decline_friend_request_button)


        init {


            itemView.setOnClickListener {
                if (friendRequsetAccepted){
                    val intent = Intent(itemView.context, ChatActivity::class.java)
                    intent.putExtra("friendId", DataManager.friendsList2[position].id)
                    intent.putExtra("friendName", DataManager.friendsList2[position].name)
                    itemView.context.startActivity(intent)
                }
            }


            itemAcceptedButton.setOnClickListener {

                val sendToName: String = friendName
                val sendToId: String = friendId

                val currentTime: Long = System.currentTimeMillis()


                val friendRequestMe = FriendClass(id = sendToId, name = sendToName,
                    image = "", accepted_me = true, accepted_contact = true, time = currentTime)

                val friendRequestTo = FriendClass(id = DataManager.currentUser.id, name = DataManager.currentUser.name,
                    image = "", accepted_me = true, accepted_contact = true, time = currentTime)

                db.collection("Friends").document(DataManager.currentUser.id).
                collection("ListOfFriends").document(sendToId)
                    .delete()
                    .addOnCompleteListener {
                        db.collection("Friends").document(sendToId).
                        collection("ListOfFriends").document(DataManager.currentUser.id).delete()
                            .addOnCompleteListener {
                                db.collection("Friends").document(DataManager.currentUser.id).
                                collection("ListOfFriends").document(sendToId)
                                    .set(friendRequestMe)
                                db.collection("Friends").document(sendToId).
                                collection("ListOfFriends").document(DataManager.currentUser.id)
                                    .set(friendRequestTo)
                            }.addOnFailureListener {
                                Log.d("!!!", "failed to registered friend request")
                            }
                    }.addOnFailureListener {
                        Log.d("!!!", "failed to registered")
                    }
            }

            itemDeclineButton.setOnClickListener {

                db.collection("Friends").document(DataManager.currentUser.id).
                collection("ListOfFriends").document(friendId).delete()
                    .addOnCompleteListener {
                        db.collection("Friends").document(friendId).
                        collection("ListOfFriends").document(DataManager.currentUser.id).delete()
                            .addOnCompleteListener {
                            }.addOnFailureListener {
                                Log.d("!!!", "failed to registered friend request")
                            }

                    }.addOnFailureListener {
                        Log.d("!!!", "failed to registered")
                    }
            }
        }
    }
}