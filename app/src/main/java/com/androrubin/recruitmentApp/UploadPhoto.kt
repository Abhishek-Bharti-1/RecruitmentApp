package com.androrubin.recruitmentApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UploadPhoto : AppCompatActivity() {

    private lateinit var db:FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth
    private var name :String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_photo)

        val mSelectedImageFileUri = intent.getStringExtra("ImageLink")?.toUri()
        val x = intent.getStringExtra("PictureType")

        Toast.makeText(this,mSelectedImageFileUri.toString(),Toast.LENGTH_SHORT).show()
        val upload_btn = findViewById<TextView>(R.id.uploadBtn)

        mAuth = FirebaseAuth.getInstance()

        val user = mAuth.currentUser
        name = user?.displayName
        val imageV = findViewById<ImageView>(R.id.imageView)
        imageV.setImageURI(mSelectedImageFileUri)

        upload_btn.setOnClickListener {
            if(mSelectedImageFileUri != null){

                val imageExtension = MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver
                    .getType(mSelectedImageFileUri!!))

                val sRef : StorageReference = FirebaseStorage.getInstance().reference
                    .child("Image "+System.currentTimeMillis() + "." + imageExtension)

                sRef.putFile(mSelectedImageFileUri!!)
                    .addOnSuccessListener { taskSnapshot ->
                        taskSnapshot.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener { url ->

                                db = FirebaseFirestore.getInstance()
                                if(x=="1"){
                                    db.collection("Users").document("$name")
                                        .update(
                                            mapOf(
                                                "LinkBannerPic" to "$url"
                                            )
                                        )
                                        .addOnSuccessListener {
                                            Log.d("Data Addition", "DocumentSnapshot written with ID: ${it}.id")
                                        }
                                        .addOnFailureListener { e ->
                                            Log.w("Data Addition", "Error adding document", e)
                                        }
                                }
                                else if(x=="2"){
                                    db.collection("Users").document("$name")
                                        .update(
                                            mapOf(
                                                "LinkProfilePic" to "$url"
                                            )
                                        )
                                        .addOnSuccessListener {
                                            Log.d("Data Addition", "DocumentSnapshot written with ID: ${it}.id")
                                        }
                                        .addOnFailureListener { e ->
                                            Log.w("Data Addition", "Error adding document", e)
                                        }
                                }
                                val intent = Intent(this,SeekerProfileView::class.java)
                                startActivity(intent)
                                finishAffinity()

                            }.addOnFailureListener {
                                Toast.makeText(this,it.message, Toast.LENGTH_LONG).show()
                                Log.e(javaClass.simpleName,it.message,it)
                            }
                    }

            }
            else{
                Toast.makeText(this,"Please select an image to upload.", Toast.LENGTH_LONG).show()
            }
        }

    }
}