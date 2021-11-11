package com.example.duiban

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.duiban.fragment.ChatsFragment
import com.example.duiban.fragment.ContactsFragment
import com.example.duiban.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val contactFragment = ContactsFragment()
    private val chatFragment = ChatsFragment()
    private val homeFragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        switchFragment(contactFragment)
        main_navigation_bar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.contact_link -> switchFragment(contactFragment)
                R.id.chat_link -> switchFragment(chatFragment)
                R.id.home_link -> switchFragment(homeFragment)
            }
            true
        }
    }

    private fun switchFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.customer_fragment_container, fragment)
            transaction.commit()
        }
    }
}