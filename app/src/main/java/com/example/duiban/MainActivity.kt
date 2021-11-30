package com.example.duiban

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.duiban.fragment.ContactsFragment
import com.example.duiban.fragment.ChatListFragment
import com.example.duiban.fragment.HomeFragment
import com.example.duiban.models.DataManager
import com.example.duiban.models.FriendClass
import com.example.duiban.models.MessageClass
import com.example.duiban.models.UserClass
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var db = FirebaseFirestore.getInstance()
    private val chatListFragment = ChatListFragment()
    private val contactFragment = ContactsFragment()
    private val homeFragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        db.collection("Messages").document(DataManager.currentUser.id).
        collection("ListOfMessages").addSnapshotListener { value, error ->
            //WHEN CHANGES IN COLLECTION HAS HAPPENED CLEAR LIST

            //val newMessageList :  MutableList<MessageClass> = mutableListOf()
            if (value != null) {
                Log.d("message data length", value.size().toString())
                DataManager.messageList.clear()
                for (document in value!!) {
                    //ITEM TO OBJECT
                    Log.d("message data", document.toString())
                    val newItem = document.toObject(MessageClass::class.java)
                    //ADD NEW ITEM TO LIST
                  //  DataManager.messageList.add(newItem)
                    DataManager.messageList.add(newItem)
                }


            }

        }

        db.collection("Users").addSnapshotListener { value, error ->
            //WHEN CHANGES IN COLLECTION HAS HAPPENED CLEAR LIST
            DataManager.friendsList.clear()


            for (document in value!!) {
                //ITEM TO OBJECT

                val newItem = document.toObject(UserClass::class.java)
                if (newItem.id != DataManager.currentUser.id){
                    //ADD NEW ITEM TO LIST
                    DataManager.friendsList.add(newItem)
                    Log.d("test get data","got data")
                }


            }
        }

        db.collection("Friends").document(DataManager.currentUser.id).
        collection("ListOfFriends").addSnapshotListener { value, error ->
            //WHEN CHANGES IN COLLECTION HAS HAPPENED CLEAR LIST
            DataManager.friendsList.clear()
            DataManager.friendsList2.clear()

            for (document in value!!) {
                //ITEM TO OBJECT

                val newItem = document.toObject(FriendClass::class.java)

                    //ADD NEW ITEM TO LIST
                    DataManager.friendsList.add(newItem)
                    DataManager.friendsList2.add(newItem)
                    Log.d("test get data","got data")



            }
        }

        when(DataManager.mainActivityState) {
            "chatListFragment" -> switchFragment(chatListFragment)
            "contactFragment" -> switchFragment(contactFragment)
            "homeFragment" -> switchFragment(homeFragment)
        }

        main_navigation_bar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.chat_list_link -> {
                    switchFragment(chatListFragment)
                    DataManager.mainActivityState = "chatListFragment"
                }
                R.id.contact_link -> {
                    switchFragment(contactFragment)
                    DataManager.mainActivityState = "contactFragment"
                }
                R.id.home_link -> {
                    switchFragment(homeFragment)
                    DataManager.mainActivityState = "homeFragment"
                }
            }
            true
        }


    }

    private fun switchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.customer_fragment_container, fragment)
        transaction.commit()
    }
}