package com.example.duiban

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.duiban.adapters.ChatAdapter
import com.example.duiban.adapters.ContactListAdapter
import com.example.duiban.models.DataManager
import com.example.duiban.models.MessageClass
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_chat.*


class ChatActivity : AppCompatActivity() {

    private var db = FirebaseFirestore.getInstance()
    private lateinit var recyclerView: RecyclerView
    private var adapter: RecyclerView.Adapter<ChatAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val sendToId: String = intent.getStringExtra("friendId")!!

        recyclerView = findViewById<RecyclerView>(R.id.chatRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ChatAdapter(sendToId)
        recyclerView.adapter = adapter


        go_back_button.setOnClickListener {
            finish()
        }

        send_message_button.setOnClickListener {
            testtext.text = message_textfield.text


            val sendToName: String = intent.getStringExtra("friendName")!!
            val message: String =  message_textfield.text.toString()
            val currentTime: Long = System.currentTimeMillis()

            val messageObject = MessageClass(idFrom = DataManager.currentUser.id, idTo = sendToId,
               nameFrom = DataManager.currentUser.name, nameTo = sendToName,
               message =  message, time = currentTime)
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
            recyclerView.adapter!!.notifyDataSetChanged()
            message_textfield.setText("")
        }

    }
}