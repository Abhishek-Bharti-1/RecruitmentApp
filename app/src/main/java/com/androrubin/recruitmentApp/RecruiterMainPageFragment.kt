package com.androrubin.recruitmentApp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class RecruiterMainPageFragment : Fragment() {

    private lateinit var dbrf : DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var detailArrayList: ArrayList<Details>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_recruiter_main_page, container, false)

        recyclerView= view.findViewById(R.id.recyclerView1)
        recyclerView.layoutManager= LinearLayoutManager(context)
        detailArrayList = arrayListOf<Details>()
        getUserData()
        return view
    }

    private fun getUserData() {


        dbrf = FirebaseDatabase.getInstance().getReference("Detail")
        dbrf.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){

                        val detail = userSnapshot.getValue(Details::class.java)
                        detailArrayList.add(detail!!)

                    }

                    // searchArrayList.addAll(detailArrayList)

                    recyclerView.adapter = MyAdapter(detailArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}