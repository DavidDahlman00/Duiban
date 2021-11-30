package com.example.duiban

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.duiban.models.DataManager
import com.example.duiban.models.FriendClass
import com.example.duiban.models.MessageClass
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_send_friend_request.*

class SendFriendRequestActivity : AppCompatActivity() {

    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_friend_request)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        send_friend_request_name.text = intent.getStringExtra("friendName")

        create_friend_request_button.setOnClickListener {

            val sendToName: String = intent.getStringExtra("friendName")!!
            val sendToId: String = intent.getStringExtra("friendId")!!
           // val message: String =  message_textfield.text.toString()
            val currentTime: Long = System.currentTimeMillis()


            val friendRequestMe = FriendClass(id = sendToId, name = sendToName,
                image = "", accepted_me = true, accepted_contact = false, time = currentTime)

            val friendRequestTo = FriendClass(id = DataManager.currentUser.id, name = DataManager.currentUser.name,
              image = "", accepted_me = false, accepted_contact = true, time = currentTime)

            db.collection("Friends").document(DataManager.currentUser.id).
            collection("ListOfFriends").document(sendToId).set(friendRequestMe)
                .addOnCompleteListener {
                    db.collection("Friends").document(sendToId).
                    collection("ListOfFriends").document(DataManager.currentUser.id).set(friendRequestTo)
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