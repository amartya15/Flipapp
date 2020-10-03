package com.example.marksofstudent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class List_of_periods_Activity : AppCompatActivity() {

    companion object{
        lateinit var table: String
        lateinit var _1st_class: TextView
        lateinit var _2nd_class: TextView
        lateinit var _3rd_class: TextView
        lateinit var _4th_class: TextView
        lateinit var _5th_class: TextView
        lateinit var _6th_class: TextView
        lateinit var _7th_class: TextView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_periods)

        _1st_class = findViewById<TextView>(R.id._1st_period)
        _2nd_class = findViewById<TextView>(R.id._2nd_period)
        _3rd_class = findViewById<TextView>(R.id._3rd_period)
        _4th_class = findViewById<TextView>(R.id._4th_period)
        _5th_class = findViewById<TextView>(R.id._5th_period)
        _6th_class = findViewById<TextView>(R.id._6th_period)
        _7th_class = findViewById<TextView>(R.id._7th_period)

        val bundle: Bundle? = intent.extras
        table = bundle?.getString("table_name") as String

        _1st_class.setOnClickListener {

            val showIntent: Intent = Intent(this,StudentViewActivity::class.java)
            showIntent.putExtra("period",_1st_class.text.toString())
            showIntent.putExtra("student_table", table)
            startActivity(showIntent)
        }
        _2nd_class.setOnClickListener {

            val showIntent: Intent = Intent(this,StudentViewActivity::class.java)
            showIntent.putExtra("period", _2nd_class.text.toString())
            showIntent.putExtra("student_table", table)
            startActivity(showIntent)
        }
        _3rd_class.setOnClickListener {

            val showIntent: Intent = Intent(this,StudentViewActivity::class.java)
            showIntent.putExtra("period", _3rd_class.text.toString())
            showIntent.putExtra("student_table", table)
            startActivity(showIntent)
        }
        _4th_class.setOnClickListener {

            val showIntent: Intent = Intent(this,StudentViewActivity::class.java)
            showIntent.putExtra("period", _4th_class.text.toString())
            showIntent.putExtra("student_table", table)
            startActivity(showIntent)
        }
        _5th_class.setOnClickListener {

            val showIntent: Intent = Intent(this,StudentViewActivity::class.java)
            showIntent.putExtra("period", _5th_class.text.toString())
            showIntent.putExtra("student_table", table)
            startActivity(showIntent)
        }
        _6th_class.setOnClickListener {

            val showIntent: Intent = Intent(this,StudentViewActivity::class.java)
            showIntent.putExtra("period", _6th_class.text.toString())
            showIntent.putExtra("student_table", table)
            startActivity(showIntent)
        }

        _7th_class.setOnClickListener {

            val showIntent: Intent = Intent(this,StudentViewActivity::class.java)
            showIntent.putExtra("period", _7th_class.text.toString())
            showIntent.putExtra("student_table", table)
            startActivity(showIntent)
        }
    }

}
