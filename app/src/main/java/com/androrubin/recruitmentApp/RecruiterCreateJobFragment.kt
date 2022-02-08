package com.androrubin.recruitmentApp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.HandlerCompat.postDelayed
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_recruiter_create_job.*

class RecruiterCreateJobFragment : Fragment() {
    private lateinit var db: DatabaseReference
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_recruiter_create_job, container, false)
        val btn=view.findViewById<Button>(R.id.sub_btn1)
//        btn.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_recruiterCreateJobFragment_to_recruiterMainPageFragment) }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val Job_des=getView()?.findViewById<TextInputLayout>(R.id.J_des)

        val post= getView()?.findViewById<TextInputEditText>(R.id.post_text1)

        post?.doOnTextChanged { text, start, before, count ->
            if(text!!.length > 30){
                post1?.error ="describe the post in description!"
            } else if(text.length<30){
                post1?.error = null
            }
        }
        val company_name= getView()?.findViewById<TextInputEditText>(R.id.c_name)
        val salary_range = getView()?.findViewById<TextInputEditText>(R.id.salary_range)
        val job_des =getView()?.findViewById<TextInputEditText>(R.id.job_des)
        job_des?.doOnTextChanged { text, start, before, count ->
            if(text!!.length > 150){
                post1?.error ="describe the post in description!"
            } else if(text.length<150){
                post1?.error = null
            }
        }


        val min_qual =getView()?.findViewById<TextInputEditText>(R.id.Min_qual)
        val experience =getView()?.findViewById<TextInputEditText>(R.id.exp_txt)
        val skills =getView()?.findViewById<TextInputEditText>(R.id.skill_txt)
        val address =getView()?.findViewById<TextInputEditText>(R.id.comadr_txt)

        var name = "Vacancy des"

        val submit = getView()?.findViewById<Button>(R.id.sub_btn1)
        submit?.setOnClickListener {

//          Toast.makeText(this,title.getText().toString(),Toast.LENGTH_SHORT).show()
            if(TextUtils.isEmpty(post?.getText()?.trim().toString())) {
                post?.setError("Post cannot be empty")
                post?.requestFocus()
            }else if (TextUtils.isEmpty(company_name?.getText()?.trim().toString())){
                company_name?.setError(" cannot be empty")
                company_name?.requestFocus()
            }else if (TextUtils.isEmpty(wrk_time.text?.trim().toString())){
                company_name?.setError(" cannot be empty")
                company_name?.requestFocus()
            }else if (TextUtils.isEmpty(min_qual?.getText()?.trim().toString())){
                company_name?.setError(" cannot be empty")
                company_name?.requestFocus()
            }else if (TextUtils.isEmpty(address?.getText()?.trim().toString())){
                company_name?.setError(" cannot be empty")
                company_name?.requestFocus()
            }
            else{

                val vac_detail = Details(
                    company_name!!.text.toString(),
                    post!!.text.toString(),
                    salary_range?.text.toString(),
                    job_des?.text.toString(),
                    wrk_time?.text.toString(),
                    min_qual?.text.toString(),
                    experience?.text.toString(),
                    skills?.text.toString(),
                    address?.text.toString()
                )

                db = FirebaseDatabase.getInstance().getReference("Detail")
                db.child(post.text.toString()).setValue(vac_detail).addOnSuccessListener {

                    company_name.text?.clear()
                    post.text?.clear()
                    salary_range?.text?.clear()
                    job_des?.text?.clear()
                    wrk_time?.text?.clear()
                    min_qual?.text?.clear()
                    experience?.text?.clear()
                    skills?.text?.clear()
                    address?.text?.clear()


                    Toast.makeText(context, "added", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

                }
                Handler().postDelayed({
                    Navigation.findNavController(view).navigate(R.id.action_recruiterCreateJobFragment_to_recruiterMainPageFragment)
                },2700)

            }
        }
    }
    }



