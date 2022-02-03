package com.androrubin.recruitmentApp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_seeker_profile_view.*
import java.io.IOException
import kotlin.time.toDuration

class SeekerProfileView : AppCompatActivity() {

    private var mSelectedImageFileUri : Uri?=null
    private lateinit var bannerPic : ImageView
    private lateinit var profilePic : ImageView
    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth
    private var name :String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seeker_profile_view)

        supportActionBar?.title="Profile"

        val deleteAccount = findViewById<TextView>(R.id.deleteAccount)
        val editProfilePic = findViewById<FloatingActionButton>(R.id.editProfilePic)
        val editBannerPic = findViewById<FloatingActionButton>(R.id.editBannerPic)
        val editProfile = findViewById<TextView>(R.id.editProfile)
        bannerPic = findViewById(R.id.bannerPic)
        profilePic = findViewById(R.id.profilePic)
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        name = user?.displayName

        db = FirebaseFirestore.getInstance()
        db.collection("Users").document("$name")
            .get()
            .addOnSuccessListener {

                //Returns value of corresponding field
                val a = it["LinkBannerPic"].toString()
                val b = it["LinkProfilePic"].toString()
                val c = it["Role"].toString()
                if(a != "")
                {
                    Glide.with(this).load(a).into(bannerPic)
                }
                if(b!= "")
                {
                    Glide.with(this).load(b).into(profilePic)
                }
                txtRole.text = c
            }
        db.collection("Seekers").document("$name")
            .get()
            .addOnSuccessListener {

                //Returns value of corresponding field
                val l = it["Name"].toString()
                val m = it["Email Id"].toString()
                val n = it["Phone Number"].toString()
                val o = it["Address"].toString()
                val p= it["Qualification"].toString()

                if(l != "" && m != "" && n != "" && o != "" && p != ""){
                    txtName.text = l
                    txtEmail.text = m
                    txtPhone.text = n
                    txtAddress.text = o
                    txtQualification.text = p
                }
            }


        logoutBtn.setOnClickListener {
            Toast.makeText(applicationContext,"User logged out ", Toast.LENGTH_SHORT).show()
            mAuth.signOut()
            val intent = Intent(this,Login_Activity::class.java)
            startActivity(intent)
            finish()
        }

        deleteAccount.setOnClickListener {
            Toast.makeText(this,"Clicked Delete Account Button", Toast.LENGTH_SHORT).show()
        }

        editBannerPic.setOnClickListener {
//            Toast.makeText(this,"Clicked Edit Banner Button", Toast.LENGTH_SHORT).show()
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                //Select Image
                val galleryintent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryintent, 222)
            }
            else {
                //Request permission
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 121)
            }
        }

        editProfilePic.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                //Select Image
                val galleryintent2 = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryintent2, 242)

            }
            else {
                //Request permission
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 141)
            }
        }

        editProfile.setOnClickListener {
            Toast.makeText(this,"Clicked Edit Profile Button", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 121) {

            val galleryintent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryintent, 222)

        }
        else if (requestCode == 141) {

            val galleryintent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryintent, 242)

        }else {

            Toast.makeText(this, "You need to allow the access of your storage if you want to upload an image", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 222){
                if(data != null){
                    try {

                        mSelectedImageFileUri = data.data!!

                        bannerPic.setImageURI(mSelectedImageFileUri)
                        val intent = Intent(this,UploadPhoto::class.java)
                        intent.putExtra("ImageLink",mSelectedImageFileUri.toString())
                        intent.putExtra("PictureType","1")
                        startActivity(intent)

                    }
                    catch (e: IOException){

                        e.printStackTrace()
                        Toast.makeText(this,"Image Selection Failed!",Toast.LENGTH_SHORT).show()

                    }
                }
            }
            if(requestCode == 242) {
                if (data != null) {
                    try {

                        mSelectedImageFileUri = data.data!!

                        profilePic.setImageURI(mSelectedImageFileUri)
                        val intent = Intent(this,UploadPhoto::class.java)
                        intent.putExtra("ImageLink",mSelectedImageFileUri.toString())
                        intent.putExtra("PictureType","2")
                        startActivity(intent)

                    } catch (e: IOException) {

                        e.printStackTrace()
                        Toast.makeText(this, "Image Selection Failed!", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}