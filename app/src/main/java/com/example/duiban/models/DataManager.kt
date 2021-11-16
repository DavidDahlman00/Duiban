package com.example.duiban.models

object DataManager {
    var currentUser: UserClass = UserClass()
    var friendsList: MutableList<UserClass> = mutableListOf()
    var messageList: MutableList<MessageClass> = mutableListOf()
}