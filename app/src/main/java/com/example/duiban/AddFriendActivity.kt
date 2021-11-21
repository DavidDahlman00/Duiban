package com.example.duiban

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.duiban.adapters.AddFriendAdapter
import com.example.duiban.adapters.ChatAdapter
import com.example.duiban.models.DataManager
import com.example.duiban.models.UserClass
import com.google.firebase.firestore.FirebaseFirestore

class AddFriendActivity : AppCompatActivity() {
    private var db = FirebaseFirestore.getInstance()
    private lateinit var recyclerView: RecyclerView
    private var adapter: RecyclerView.Adapter<AddFriendAdapter.ViewHolder>? = null
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById<RecyclerView>(R.id.addfriend_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AddFriendAdapter()
        recyclerView.adapter = adapter

        db.collection("Users").addSnapshotListener { value, error ->
            //WHEN CHANGES IN COLLECTION HAS HAPPENED CLEAR LIST
            DataManager.usersList.clear()


            for (document in value!!) {
                //ITEM TO OBJECT

                val newItem = document.toObject(UserClass::class.java)
                if (newItem.id != DataManager.currentUser.id){
                    //ADD NEW ITEM TO LIST
                    DataManager.usersList.add(newItem)
                    Log.d("test get data","got data")
                }
            }
            recyclerView.adapter!!.notifyDataSetChanged()
        }
    }
}