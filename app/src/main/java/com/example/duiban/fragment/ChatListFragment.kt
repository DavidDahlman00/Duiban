package com.example.duiban.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.duiban.R
import com.example.duiban.adapters.ChatListAdapter
import com.example.duiban.models.DataManager
import com.example.duiban.models.MessageClass
import com.google.firebase.firestore.FirebaseFirestore


class ChatListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var adapter: RecyclerView.Adapter<ChatListAdapter.ViewHolder>? = null
    private var db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =inflater.inflate(R.layout.fragment_chatlist, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.chat_list_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = ChatListAdapter()
        recyclerView.adapter = adapter
        db.collection("Messages").document(DataManager.currentUser.id).
        collection("ListOfMessages").addSnapshotListener { value, error ->
            recyclerView.adapter!!.notifyDataSetChanged()
        }
        recyclerView.adapter!!.notifyDataSetChanged()
        return view
    }
}