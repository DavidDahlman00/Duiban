package com.example.duiban

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.duiban.adapters.AddFriendAdapter
import com.example.duiban.adapters.ChatAdapter
import com.example.duiban.models.DataManager
import com.example.duiban.models.UserClass
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_friend.*

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



        searchContactView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("!!!", "text submit")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("!!!", newText as String)
                if (newText!=""){
                    DataManager.usersList2.clear()
                    var search = newText?.toLowerCase() as String
                    for (user in DataManager.usersList){
                        if(user.name.toLowerCase().contains(search)){
                           DataManager.usersList2.add(user)

                        }
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }

                }else{
                    DataManager.usersList2.clear()
                    DataManager.usersList2 = setSearchList()
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                return true
            }

        })
    }
    private fun setSearchList(): MutableList<UserClass> {
        var searchList: MutableList<UserClass> = mutableListOf()
        for(user in DataManager.usersList){

                searchList.add(user)

        }

        return searchList
    }
}