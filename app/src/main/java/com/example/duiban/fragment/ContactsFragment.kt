package com.example.duiban.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.duiban.AddFriendActivity
import com.example.duiban.R
import com.example.duiban.adapters.ContactsAdapter
import kotlinx.android.synthetic.main.fragment_contacts.view.*


class ContactsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var adapter: RecyclerView.Adapter<ContactsAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_contacts, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.contacts_recyclervieew)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = ContactsAdapter()
        recyclerView.adapter = adapter

        view.add_friend_floatingActionButton.setOnClickListener {
            val intent = Intent(view.context, AddFriendActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}