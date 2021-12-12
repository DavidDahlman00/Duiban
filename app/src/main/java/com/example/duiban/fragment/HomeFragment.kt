package com.example.duiban.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.duiban.*
import com.example.duiban.models.DataManager
import com.example.duiban.models.UserClass
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {
     lateinit var testText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_home, container, false)
        testText = view.findViewById(R.id.hometesttext)

        testText.text = DataManager.currentUser.name

        view.profile_button.setOnClickListener {
            val intent = Intent(view.context, ProfileActivity::class.java)
            startActivity(intent)
        }

        view.logout_button.setOnClickListener {
            DataManager.currentUser = UserClass()
            DataManager.friendsList = mutableListOf()
            DataManager.friendsList2 = mutableListOf()
            DataManager.usersList = mutableListOf()
            DataManager.usersList2 = mutableListOf()
            DataManager.messageList = mutableListOf()
            DataManager.profileImageReference = mutableListOf()
            DataManager.mainActivityState = "HomeFragment"

            val intent = Intent(view.context, LoginActivity::class.java)
            startActivity(intent)

        }

        return view
    }

}