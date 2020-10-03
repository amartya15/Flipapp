package com.example.marksofstudent

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import android.app.ProgressDialog
import java.util.*
import javax.net.ssl.HttpsURLConnection
import kotlin.collections.ArrayList


class StudentViewActivity : AppCompatActivity() {

    companion object {
        lateinit var recycler_view: RecyclerView
        lateinit var table: String
        lateinit var period: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_student)


        val bundle: Bundle? = intent.extras
        table = bundle?.getString("student_table") as String
        period = bundle.getString("period") as String


        //val small_table = table.toLowerCase()
        recycler_view = findViewById<RecyclerView>(R.id.recyclerView)
        //val url: String = "http://192.168.1.206/Faculty_Login/example.php"
        //Router IP Address

        val url: String = "https://flipappjis.000webhostapp.com/" +
                table.toLowerCase(Locale.ENGLISH)+".php"
        //Moblie IP address
        //val url: String = "http://192.168.43.186/Faculty_Login/single.php"
        val userBackTask: UserBackTask = UserBackTask(this)
        userBackTask.execute(url)

    }

    class UserBackTask internal constructor(internal var context: Context): AsyncTask<String,Void,String>(){

        /*companion object {
            lateinit var dialog: ProgressDialog
        }*/
        val showProgress: ProgressDialog = ProgressDialog(context)

        override fun onPreExecute() {

            //showProgress.setTitle("Please wait")
            showProgress.setMessage("Please wait")
            showProgress.setCancelable(false)
            showProgress.show()
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String): String? {

            val view_url = params[0]
            val url = URL(view_url)
            try {

                val httpURLConnection = url.openConnection() as HttpsURLConnection
                val inputStream: InputStream = httpURLConnection.getInputStream()
                val inputStreamReader: InputStreamReader = InputStreamReader(inputStream, "iso-8859-1")

                val string: StringBuilder = StringBuilder()
                //var result = ""
                val bufferedReader = BufferedReader(inputStreamReader)
                try {

                    var line = bufferedReader.readLine()
                    while (line != null) {
                        //result += line
                        //print(result)
                        println()
                        string.append(line)
                        print("string : " + string)
                        line = bufferedReader.readLine()
                    }
                } finally {
                    bufferedReader.close()
                }

                //var data = inputStreamReader.read()
                /*var data = inputStreamReader.readLines()
                while (data != -1) {
                    result += data.toChar()
                    data = inputStreamReader.read()
                    print(result)
                }*/
                //val res: String = toString()
                inputStream.close()
                inputStreamReader.close()
                httpURLConnection.disconnect()
                val result = string.toString()
                println()
                println("result : " + result)
                return result


                //Shankar

                /*val reader = BufferedReader(inputStream.reader())
                val Id = StringBuilder()
                try {
                    var line = reader.readLine()
                    while (line != null) {
                            Id.append(line)

                        line = reader.readLine()
                    }
                } finally {
                    reader.close()
                }


                println(Id)

               val jj = Id.split(",").toTypedArray()
                val ids: ArrayList<String> = ArrayList()
                var size=(jj.size)-1
                var last=jj[size]
                var temp=0


                for(i in 0 until size){
                    if(temp < last.toInt()) {
                        ids.add(jj[i])
                        temp++
                    }

                }
                println(ids)*/

            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            val alertDialog: AlertDialog
            alertDialog = AlertDialog.Builder(context).create()
            if (result == null) {
                if(showProgress.isShowing)
                {
                    showProgress.dismiss()
                }
                alertDialog.setTitle("Error")
                alertDialog.setMessage("Can't Connect with server. Please check your internet connection")
                alertDialog.show()
            }
            else {
                if(showProgress.isShowing)
                {
                    showProgress.dismiss()
                }

                try {

                    Toast.makeText(context, "Here", Toast.LENGTH_LONG).show()
                    val jsonArray: JSONArray = JSONArray(result)
                    /*val jsonObject: JSONObject = jsonArray.getJSONObject(0)

                    println()
                    println("jsonObject : " + jsonObject)
                    println()
                    print("Length : " + jsonArray.length())
                    println()
                    println(jsonObject.getInt("ID"))
                    println(jsonObject.getString("NAME"))
                    println(jsonObject.getString("EMAIL"))*/

                    // implement for loop for getting users list data

                    val student_name: ArrayList<String> = ArrayList()
                    val Roll_No: ArrayList<String> = ArrayList()

                    for (index in 0 until jsonArray.length()) {
                        // create a JSONObject for fetching single user data
                        val jsonObject: JSONObject = jsonArray.getJSONObject(index)
                        Roll_No.add(jsonObject.getString("ROLL_NO"))
                        student_name.add(jsonObject.getString("NAME"))
                    }
                    /*val Roll: ArrayList<String> = ArrayList()
                    for(pos in 0 until Roll_No.size){
                        Roll.add(Roll_No.get(pos).toString())
                        ////Roll[pos]=Roll_No[pos].toString()
                    }*/


                    val mAdapter: ShowStudentAdapter = ShowStudentAdapter(context, Roll_No, student_name)
                    recycler_view.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                    recycler_view.adapter = mAdapter

                    Toast.makeText(context,"Success : " + table,Toast.LENGTH_SHORT).show()
                    Toast.makeText(context,"Period : " + period,Toast.LENGTH_SHORT).show()

                    println()
                    print("Roll : " + Roll_No)
                    println()
                    print("Names : " + student_name)
                    println()
                    println("JSONArray " + jsonArray.toString())
                    println()
                    println("JSON array size: " + jsonArray.length())


                    println()
                    print("ID size: " + Roll_No.size)
                    println()
                    print("email size: " + student_name.size)
                }
                catch (e: JSONException)
                {
                    e.printStackTrace()
                }
            }
        }
    }
}
