package com.example.duiban.models

class FriendClass(var id: String = "",
                  var name: String = "",
                  var image: String = "",
                  var accepted_me: Boolean = true,
                  var accepted_contact: Boolean = false,
                  val time: Long = 0)
{}