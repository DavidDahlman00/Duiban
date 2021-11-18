package com.example.duiban

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.duiban.models.DataManager
import com.example.duiban.models.MessageClass
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_chat.*


class ChatActivity : AppCompatActivity() {

    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val sendToId: String = intent.getStringExtra("friendId")!!


        go_back_button.setOnClickListener {
            finish()
        }

        send_message_button.setOnClickListener {
            testtext.text = message_textfield.text


            val sendToName: String = intent.getStringExtra("friendName")!!
            val message: String =  message_textfield.text.toString()
            val currentTime: Long = System.currentTimeMillis()

            val messageObject = MessageClass(DataManager.currentUser.id, sendToId, sendToName, DataManager.currentUser.name,
                message, currentTime)
            db.collection("Messages").document(sendToId).
            collection("ListOfMessages").document().set(messageObject)
                .addOnCompleteListener {


                }.addOnFailureListener {
                    Log.d("!!!", "failed to registerd")
                }
            db.collection("Messages").document(DataManager.currentUser.id).
            collection("ListOfMessages").document().set(messageObject)
                .addOnCompleteListener {


                }.addOnFailureListener {
                    Log.d("!!!", "failed to registerd")
                }
        }

    }
}