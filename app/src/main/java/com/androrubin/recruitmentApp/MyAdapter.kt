package com.androrubin.recruitmentApp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter( private val detailList :ArrayList<Details> ): RecyclerView.Adapter<MyAdapter.MyViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_row, parent ,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = detailList[position]
        holder.text1.text = currentitem.post
        holder.text2.text = currentitem.company_name
        holder.text3.text = currentitem.salary_range
        holder.text4.text = currentitem.job_des
        holder.text5.text = currentitem.working_hrs
        holder.text6.text = currentitem.min_qual
        holder.text7.text = currentitem.experience
        holder.text8.text = currentitem.skills
        holder.text9.text = currentitem.address
        //holder.button.get = currentitem.post


    }

    override fun getItemCount(): Int {

        return detailList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
        val  text1:TextView =itemView.findViewById(R.id.text_rec1)
        val  text2:TextView =itemView.findViewById(R.id.text_rec2)
        val  text3:TextView =itemView.findViewById(R.id.text_rec3)
        val  text4:TextView =itemView.findViewById(R.id.text_rec4)
        val  text5:TextView =itemView.findViewById(R.id.text_rec5)
        val  text6:TextView =itemView.findViewById(R.id.text_rec6)
        val  text7:TextView =itemView.findViewById(R.id.text_rec7)
        val  text8:TextView =itemView.findViewById(R.id.text_rec8)
        val  text9:TextView =itemView.findViewById(R.id.text_rec9)
       // val  button:TextView =itemView.findViewById(R.id.button1)


    }


}