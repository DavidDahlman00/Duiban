package com.example.duiban.models

object DataManager {
    var currentUser: UserClass = UserClass()
    var friendsList: MutableList<UserClass> = mutableListOf()
    var friendsList2: MutableList<FriendClass> = mutableListOf()
    var usersList:  MutableList<UserClass> = mutableListOf()
    var usersList2:  MutableList<UserClass> = mutableListOf()
    var messageList: MutableList<MessageClass> = mutableListOf()
    var mainActivityState: String = "HomeFragment"
}