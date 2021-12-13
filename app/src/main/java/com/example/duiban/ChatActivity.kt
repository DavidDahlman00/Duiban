package com.example.duiban

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.doOnTextChanged

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.duiban.adapters.ChatAdapter
import com.example.duiban.models.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.squareup.okhttp.Dispatcher
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ChatActivity : AppCompatActivity() {

    private var db = FirebaseFirestore.getInstance()
    private lateinit var recyclerView: RecyclerView
    private var adapter: RecyclerView.Adapter<ChatAdapter.ViewHolder>? = null
    private var audioTextStatus = "audio"

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
        recyclerView.smoothScrollToPosition(DataManager.messageList.filter { (it.idFrom == sendToId) or (it.idTo == sendToId)}.size);
        send_audio_button.visibility = View.INVISIBLE

        message_textfield.doOnTextChanged { text, _, _, _ ->
            if (text == ""){
                send_message_button.setImageResource(R.drawable.ic_baseline_mic_24)
                audioTextStatus = "audio"
            }else{
                send_message_button.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                audioTextStatus = "text"
                send_audio_button.visibility = View.INVISIBLE
            }
        }

        send_message_button.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if(audioTextStatus == "audio"){
                    when (event?.action) {
                        MotionEvent.ACTION_DOWN -> {

                            Log.d("!!!","press down")}
                        MotionEvent.ACTION_UP -> {
                            send_audio_button.visibility = View.VISIBLE
                            Log.d("!!!","press up")}
                    }
                }
                return v?.onTouchEvent(event) ?: true
            }
        })
        
        send_message_button.setOnClickListener {
            if(audioTextStatus == "text"){
                sendTextMessage(sendToId = sendToId)
            }
            hideKeyboard()

        }

    }

    private fun sendTextMessage(sendToId : String){
        val sendToName: String = intent.getStringExtra("friendName")!!
        val message: String =  message_textfield.text.toString()
        val currentTime: Long = System.currentTimeMillis()

        val messageObject = MessageClass(idFrom = DataManager.currentUser.id, idTo = sendToId,
            nameFrom = DataManager.currentUser.name, nameTo = sendToName,
            message =  message, time = currentTime)

        val toNotification = "/topics/$sendToId"
        PushNotification(NotificationData(title = DataManager.currentUser.name, message = message), to = toNotification)
            .also { sendNotification(it)
                Log.d("Notification!!!","Help")}

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


    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun hideKeyboard(){
        // since our app extends AppCompatActivity, it has access to context
        val imm=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        // we have to tell hide the keyboard from what. inorder to do is we have to pass window token
        // all of our views,like message, name, button have access to same window token. since u have button
        imm.hideSoftInputFromWindow(send_message_button.windowToken, 0)

        // if you are using binding object
        // imm.hideSoftInputFromWindow(binding.button.windowToken,0)

    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch{
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if (response.isSuccessful){
                Log.d("!!!", "Response: ${Gson().toJson(response)}")
            }
        } catch (e: Exception){
            Log.e("!!!", e.toString())
        }
    }
}


