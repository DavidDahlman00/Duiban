package com.example.duiban

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.duiban.adapters.ChatAdapter
import com.example.duiban.models.DataManager
import com.example.duiban.models.MessageClass
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    private var db = FirebaseFirestore.getInstance()
    private lateinit var recyclerView: RecyclerView
    private var adapter: RecyclerView.Adapter<ChatAdapter.ViewHolder>? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sendToId: String = intent.getStringExtra("friendId")!!

        recyclerView = findViewById<RecyclerView>(R.id.chatRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ChatAdapter(sendToId)
        recyclerView.adapter = adapter


        send_message_button.setOnClickListener {



            val sendToName: String = intent.getStringExtra("friendName")!!
            val message: String =  message_textfield.text.toString()
            val currentTime: Long = System.currentTimeMillis()

            val messageObject = MessageClass(idFrom = DataManager.currentUser.id, idTo = sendToId,
               nameFrom = DataManager.currentUser.name, nameTo = sendToName,
               message =  message, time = currentTime)
            db.collection("Messages").document(sendToId).
            collection("ListOfMessages").document().set(messageObject)
                .addOnCompleteListener {
                    db.collection("Messages").document(DataManager.currentUser.id).
                    collection("ListOfMessages").document().set(messageObject)
                        .addOnCompleteListener {
                            recyclerView.adapter!!.notifyDataSetChanged()
                            message_textfield.setText("")
                            showSoftKeyboard(message_textfield)

                        }.addOnFailureListener {
                            Log.d("!!!", "failed to registerd")
                        }

                }.addOnFailureListener {
                    Log.d("!!!", "failed to registerd")
                }


        }

    }

    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}