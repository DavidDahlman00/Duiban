package com.example.duiban.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.duiban.R
import com.example.duiban.models.DataManager
import kotlinx.android.synthetic.main.fragment_home.*


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
        return view
    }

}