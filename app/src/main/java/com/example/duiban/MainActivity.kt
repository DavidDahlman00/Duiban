package com.example.duiban

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.duiban.fragment.ChatsFragment
import com.example.duiban.fragment.ContactsFragment
import com.example.duiban.fragment.HomeFragment
import com.example.duiban.models.DataManager
import com.example.duiban.models.UserClass
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var db = FirebaseFirestore.getInstance()
    private val contactFragment = ContactsFragment()
    private val chatFragment = ChatsFragment()
    private val homeFragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        switchFragment(homeFragment)
        main_navigation_bar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.contact_link -> switchFragment(contactFragment)
                R.id.chat_link -> switchFragment(chatFragment)
                R.id.home_link -> switchFragment(homeFragment)
            }
            true
        }

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