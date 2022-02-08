package com.androrubin.recruitmentApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class Vacancy_details : AppCompatActivity() {

    private lateinit var dbrf : DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var detailArrayList: ArrayList<Details>

    // private lateinit var searchArrayList: ArrayList<Details>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vacancy_details)

        supportActionBar?.hide()

        recyclerView= findViewById(R.id.recyclerView1)
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.setHasFixedSize(true)

        //  searchArrayList = arrayListOf<Details>()

        detailArrayList = arrayListOf<Details>()
        getUserData()

    }

    /* override fun onCreateOptionsMenu(menu: Menu?): Boolean {

         menuInflater.inflate(R.menu.menu_item,menu)
         val item = menu?.findItem(R.id.search_action)
         val searchView = item?.actionView as SearchView
         searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

             override fun onQueryTextSubmit(query : String?): Boolean {

             }

             override fun onQueryTextChange(newText: String?): Boolean {

                 searchArrayList.clear()
                 val searchText = newText!!.toLowerCase(Locale.getDefault())

                 if(searchText.isNotEmpty()){

                     searchArrayList.forEach {
                         if (it.post?.toLowerCase(Locale.getDefault())!!.contains(searchText)) {

                             searchArrayList.add(it)
                         }

                     }
                     recyclerView.adapter!!.notifyDataSetChanged()
                 }else{

                     searchArrayList.clear()
                     searchArrayList.addAll(detailArrayList)
                     recyclerView.adapter!!.notifyDataSetChanged()
                 }

                 return false
             }

         })

         return super.onCreateOptionsMenu(menu)
     }*/

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