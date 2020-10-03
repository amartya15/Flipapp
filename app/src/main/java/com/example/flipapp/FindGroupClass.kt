package com.example.marksofstudent

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class FindGroupClass internal constructor(internal var context: Context): AsyncTask<String, Void, String>(){

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

            val httpURLConnection = url.openConnection() as HttpURLConnection
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
                StudentViewActivity.recycler_view.layoutManager = LinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL,false)
                StudentViewActivity.recycler_view.adapter = mAdapter

                Toast.makeText(context,"Success : " + StudentViewActivity.table, Toast.LENGTH_LONG).show()

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
