package com.example.marksofstudent

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_class.*

class List_Of_Class_By_Faculty : AppCompatActivity() {

    lateinit var faculty: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class)

        val bundle: Bundle? = intent.extras
        val faculty_name = bundle?.getString("faculty_name")
        val group: ArrayList<String> = bundle?.getStringArrayList("ClassesArray") as ArrayList<String>        /*cla.setText(group[0])
        cla.setOnClickListener {
            Toast.makeText(this,cla.text.toString(),Toast.LENGTH_SHORT).show()
        }*/

        faculty = findViewById(R.id.tv)
        faculty.setText("Welcome\n"+faculty_name)

        val classAdapter: List_of_ClassAdapter = List_of_ClassAdapter(this,group)

        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = classAdapter
    }
}
