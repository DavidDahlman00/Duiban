package com.example.duiban.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.duiban.AddFriendActivity
import com.example.duiban.MainActivity
import com.example.duiban.ProfileActivity
import com.example.duiban.R
import com.example.duiban.models.DataManager
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

        return view
    }

}