package com.example.duiban

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.duiban.fragment.Camera_Gallery_bottom_sheet_fragment
import com.example.duiban.models.DataManager
import com.example.duiban.models.UserClass
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.ByteArrayOutputStream
import java.util.*
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity() {

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
    }

    private var takenImage: Bitmap? = null
    lateinit var uuid: String
    val db = FirebaseFirestore.getInstance()
    private var https: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bottomSheet = Camera_Gallery_bottom_sheet_fragment()

        if (DataManager.currentUser.profileImage == ""){
            profile_image.setImageResource(R.drawable.ic_baseline_photo_camera_24)
        }else{
            Picasso.get().load(DataManager.currentUser.profileImage).into(profile_image)
        }

        profile_image.setOnClickListener {
            bottomSheet.show(supportFragmentManager,"camera_gallery_bottom_sheet")
        }

        update_profile_button.setOnClickListener {
            updateUserToDatabase()
            bottomSheet.show(supportFragmentManager,"camera_gallery_bottom_sheet")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }else{
                Log.d("camera", "permission denied")
                Toast.makeText(this,"You just denied the permission for the camera. " +
                        "Don't worry you can allow it in settings.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
             takenImage = data!!.extras!!.get("data") as Bitmap
            profile_image.setImageBitmap(takenImage)
        }
    }

    fun cameraFunction(){
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED){
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        }else{
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE)

        }
    }

    fun galleryFunction(){

    }

    fun updateUserToDatabase(){
        uuid = UUID.randomUUID().toString()
        val baos = ByteArrayOutputStream()
        val storageRef = FirebaseStorage.getInstance()
            .reference.child(DataManager.currentUser.id).child("ProfileImage/$uuid")

        if (takenImage == null) return

        takenImage!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = storageRef.child("images/" + UUID.randomUUID().toString()).putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
            Log.d("!!!", "on failure  $it")
        }.addOnSuccessListener { taskSnapshot ->
            val url = taskSnapshot.metadata?.reference?.downloadUrl


            url?.addOnSuccessListener {
                val link = it
                Log.d("url", "$it")
                https = link.toString()
                Log.d("Photo Link", "$https")
                val name = DataManager.currentUser.name
                val id = DataManager.currentUser.id
                val email = DataManager.currentUser.email
                val password = DataManager.currentUser.password
                val newUserDetails = UserClass(id= id, name = name, email = email, password = password ,profileImage = https)

                db.collection("Users").document(id).set(newUserDetails)

                Log.d("USER UPDATE", "to the database, good")
            }
        }
    }

}