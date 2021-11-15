package com.example.duiban

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.duiban.models.UserClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var userName: EditText
    lateinit var password: EditText
    var db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        email = findViewById(R.id.registertextEmail)
        userName = findViewById(R.id.registertextUsername)
        password = findViewById(R.id.registereditTextTextPassword)

        registercreatebutton.setOnClickListener {
          if(tryToRegister()){
              registerUser()
          }else{
              Toast.makeText(this@RegisterActivity, "test", Toast.LENGTH_SHORT).show()
          }
        }

        gobackloginbutton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun tryToRegister(): Boolean{
        if(userName.text.trim{it <= ' '}.isEmpty()){
            Log.d("!!!", "no username")
            errorRegister.text = "enter a username"
            return false
        }
        if(email.text.trim{it <= ' '}.isEmpty()){
            Log.d("!!!", "no username")
            errorRegister.text = "enter a email"
            return false
        }
        if(password.text.trim{it <= ' '}.isEmpty()){
            Log.d("!!!", "no username")
            errorRegister.text = "enter a password"
            return false
        }
        errorRegister.text = ""
        return true
    }

    fun registerUser(){
        val username = userName.text.trim{it <= ' '}.toString()
        val email = email.text.trim{it <= ' '}.toString()
        val password = password.text.trim{it <= ' '}.toString()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
            { task ->
                if(task.isSuccessful){
                    val userID = task.result?.user?.uid.toString()
                    if (userID != null){
                     val newUser = UserClass(id = userID, name = username, email = email, password = password)
                        db.collection("Users").document(userID).set(newUser)
                            .addOnCompleteListener {
                                    Log.d("!!!", "${username} successfully registerd")
                            }.addOnFailureListener{
                                Log.d("!!!", "failed to registerd")
                            }
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("user_id", userID)
                        intent.putExtra("email", email)
                        intent.putExtra("password", password)
                        startActivity(intent)
                        finish()
                    }

                }else{
                    errorRegister.text = "failed to register"
                }
            }
        )
    }


}