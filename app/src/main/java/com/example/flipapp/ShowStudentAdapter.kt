package com.example.marksofstudent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

public class ShowStudentAdapter(context: Context, Roll_No: ArrayList<String>, student_name: ArrayList<String>): RecyclerView.Adapter<ShowStudentAdapter.ViewHolder>() {


    val ctx: Context = context
    val user_roll: ArrayList<String> = ArrayList(Roll_No)
    val user_Names: ArrayList<String> = ArrayList(student_name)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowStudentAdapter.ViewHolder {
        //Getting the view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_view,parent,false)
        //Returning to the ViewHolder class
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return user_Names.size
    }

    override fun onBindViewHolder(holder: ShowStudentAdapter.ViewHolder, position: Int) {
        holder.roll.setText(user_roll.get(position))
        holder.name.setText(user_Names.get(position))
        holder.present.setOnClickListener {
            Toast.makeText(ctx,"Present",Toast.LENGTH_LONG).show()
        }
        holder.absent.setOnClickListener {
            Toast.makeText(ctx,"Absent",Toast.LENGTH_LONG).show()
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val present = itemView.findViewById<ImageView>(R.id.design_present)
        val absent = itemView.findViewById<ImageView>(R.id.design_absent)
        val roll: TextView = itemView.findViewById(R.id.design_roll_no)
        val name: TextView = itemView.findViewById(R.id.design_name)
    }
}