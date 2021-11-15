package com.example.duiban

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.EditText
import com.example.duiban.models.DataManager
import com.example.duiban.models.UserClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private var showPassword = false
    private var db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        email = findViewById(R.id.logintextUseremail)
        password = findViewById(R.id.editTextTextPassword)
        imagelogineye.setOnClickListener {
            if (showPassword){
                imagelogineye.setImageResource(R.drawable.ic_baseline_remove_red_eye_24)
                password.transformationMethod = PasswordTransformationMethod.getInstance()
            }else{
                imagelogineye.setImageResource(R.drawable.ic_baseline__eye_crossover)
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()

            }
            showPassword = !showPassword
        }

        loginbutton.setOnClickListener {
            if (tryToLogin()){
                loginUser()
            }
        }

        registerbutton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    @SuppressLint("SetTextI18n")
    private fun tryToLogin(): Boolean {

        if(email.text.trim{it <= ' '}.isEmpty()){
            Log.d("!!!", "no username")
            loginError.text = "enter a email"
            return false
        }
        if(password.text.trim{it <= ' '}.isEmpty()){
            Log.d("!!!", "no username")
            loginError.text = "enter a password"
            return false
        }
        loginError.text = ""
        return true
    }

    @SuppressLint("SetTextI18n")
    fun loginUser(){
        val email = email.text.trim{it <= ' '}.toString()
        val password = password.text.trim{it <= ' '}.toString()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val userID = task.result?.user?.uid.toString()
                Log.d("login", "userID: $userID")
                db.collection("Users").document(userID).addSnapshotListener { document, e ->
                    if (e != null){
                        Log.d("login", "data failed to load")
                    }
                    if (document != null && document.exists()){
                        val userdata = document.toObject(UserClass::class.java)
                        Log.d("login", userdata.toString())
                        if(userdata != null){
                            DataManager.currentUser = userdata
                            Log.d("login", "got data ${DataManager.currentUser.id}")
                            Log.d("login", "got data name ${DataManager.currentUser.name}")
                            loginError.text = ""
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("user_id", DataManager.currentUser.id)
                            intent.putExtra("email", DataManager.currentUser.email)
                            intent.putExtra("password", DataManager.currentUser.password)
                            startActivity(intent)
                            finish()
                        }

                    }else{
                        Log.d("login", "data: null")
                    }
                }
            }else{
                Log.d("login", "failed to login")
                loginError.text = "no account with current email and password"
            }
        }
    }
}