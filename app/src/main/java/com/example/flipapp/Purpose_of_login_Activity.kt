package com.example.marksofstudent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Purpose_of_login_Activity : AppCompatActivity() {

    lateinit var give_marks: Button
    lateinit var view_marks: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purpose_of_login_)

        val bundle: Bundle? = intent.extras
        val faculty_name = bundle?.getString("faculty_name")
        val group: ArrayList<String> = bundle?.getStringArrayList("ClassesArray") as ArrayList<String>


        give_marks = findViewById(R.id.give_marks)
        view_marks = findViewById(R.id.view_marks)

        give_marks.setOnClickListener {

            val intent = Intent(this,List_Of_Class_By_Faculty::class.java)
            intent.putExtra("ClassesArray",group)
            intent.putExtra("faculty_name",faculty_name)
            startActivity(intent)

        }
        view_marks.setOnClickListener {
            val intent = Intent(this,List_Of_Class_By_Faculty::class.java)
            intent.putExtra("ClassesArray",group)
            intent.putExtra("faculty_name",faculty_name)
            startActivity(intent)
        }
    }
}
