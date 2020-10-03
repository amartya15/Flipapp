package com.example.marksofstudent

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

public class List_of_ClassAdapter(context: Context, classes: ArrayList<String>): RecyclerView.Adapter<List_of_ClassAdapter.ViewHolder>() {

    val ctx: Context = context
    val list_class: ArrayList<String> = classes

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): List_of_ClassAdapter.ViewHolder {
        //Getting the view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.design_view_classes,parent,false)
        //Returning to the ViewHolder class
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list_class.size
    }

    override fun onBindViewHolder(holder: List_of_ClassAdapter.ViewHolder, position: Int) {
        holder.each_classes.setText(list_class.get(position))
        holder.each_classes.setOnClickListener {
            //val groupName = holder.each_classes.text.toString()

            val timingIntent: Intent = Intent(ctx,List_of_periods_Activity::class.java)
            timingIntent.putExtra("table_name",holder.each_classes.text.toString())
            ctx.startActivity(timingIntent)


            /*val showIntent: Intent = Intent(ctx,StudentViewActivity::class.java)
            showIntent.putExtra("table_name",holder.each_classes.text.toString())
            ctx.startActivity(showIntent)*/
            //Toast.makeText(ctx,holder.each_classes.text.toString(),Toast.LENGTH_LONG).show()
        }

    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val each_classes: TextView = itemView.findViewById(R.id.design_class)
    }
}