package com.example.marksofstudent

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var etPassword: EditText
    }

    lateinit var etID: EditText
        lateinit var login: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etID = findViewById(R.id.editText_ID)
        etPassword = findViewById(R.id.editText_password)
        login = findViewById(R.id.login_btn)

        etID.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkInputs()
            }
        })
        etPassword.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkInputs()
            }
        })

        login.setOnClickListener {
            val etxtID = etID.text.toString()
            val etxtPassword = etPassword.text.toString()
            val login_url = "https://flipappjis.000webhostapp.com/login.php"
            //val login_url = "http://localhost/Flip_Marks/Faculty_Login/login.php"
            val faculty_login: facultyLoginTask = facultyLoginTask(this)
            faculty_login.execute(login_url,etxtID,etxtPassword)
        }
    }

    class facultyLoginTask internal constructor(internal var context: Context) : AsyncTask<String,String,String>(){

        val showProgress: ProgressDialog = ProgressDialog(context)
        /*var url: String? = ""
        var ID: String? = ""
/        var PASSWORD: String? = ""*/
        override fun doInBackground(vararg params: String?): String? {

            val url = params[0]
            val ID = params[1]
            val PASSWORD = params[2]
            val logged_in_url: URL = URL(url)
            try {
                val httpURLConnection: HttpsURLConnection = logged_in_url.openConnection() as HttpsURLConnection
                //Set the requewst method whether to send ("POST")
                httpURLConnection.setRequestMethod("POST")
                httpURLConnection.setDoOutput(true)
                httpURLConnection.setDoInput(true)
                val outputStream: OutputStream = httpURLConnection.getOutputStream()
                val outputStreamWriter : OutputStreamWriter = OutputStreamWriter(outputStream, "UTF-8")
                val bufferedWriter: BufferedWriter = BufferedWriter(outputStreamWriter)
                val login_data = URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(ID,"UTF-8")+
                        "&"+URLEncoder.encode("PASSWORD","UTF-8")+"="+URLEncoder.encode(PASSWORD,"UTF-8")
                bufferedWriter.write(login_data)
                bufferedWriter.flush()
                bufferedWriter.close()
                outputStream.close()
                outputStreamWriter.close()

                val inputStream: InputStream = httpURLConnection.inputStream
                val inputStreamReader: InputStreamReader = InputStreamReader(inputStream,"ISO-8859-1")
                val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)

                var result: String = ""
                try {
                    var line = bufferedReader.readLine()
                    while (line != null) {
                        result += line
                        line = bufferedReader.readLine()
                    }
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
                bufferedReader.close()
                inputStream.close()
                inputStreamReader.close()
                httpURLConnection.disconnect()
                return result
            }
            catch (e: Exception){
                e.printStackTrace()
            }
            return null
        }

        override fun onPreExecute() {
            showProgress.setMessage("Please wait")
            showProgress.setCancelable(false)
            showProgress.show()
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {
            if(showProgress.isShowing){
                showProgress.dismiss()
            }
            if(result == "0"){
                etPassword.text.clear()
                etPassword.requestFocus()
                Toast.makeText(context,"Invalid ID or Password",Toast.LENGTH_LONG).show()
            }
            else if(result == null){
                val alertDialog: AlertDialog = AlertDialog.Builder(context).create()
                alertDialog.setTitle("Error")
                alertDialog.setMessage("Can't connect,\nPlease check your internet connection....")
                alertDialog.show()
            }
            else{
                println(result)
                //val classes = listOf(result).toString()
                //val get = result.toString()
                //val strs = result.toString().split(",").toTypedArray()
                //val strs = get.split(",").toTypedArray()
                //val strs = classes.split(",").toTypedArray()
                /*for(i in 0 until strs.size){
                    println(strs[i])
                }
                //println(get)
                println(strs[0])*/
                val list_of_classes: ArrayList<String> = ArrayList()
                var arr = result

                val Classes = arr.split(",").toTypedArray()
                Classes[0] = Classes[0].removePrefix("\"")
                Classes[Classes.size-2] = Classes[Classes.size-2].removeSuffix("\"")
                Classes[Classes.size-1] = Classes[Classes.size-1].removeSuffix("\"")
                Classes[Classes.size-1] = Classes[Classes.size-1].removePrefix("\"")

                println(Classes[0])
                println(Classes[1])
                println(Classes[4])
                val size = Classes.size
                //until means < not <=
                for(i in 0 until (size-1)){
                    list_of_classes.add(Classes[i])
                    println(Classes[i])
                }
                println(Classes[0])
                val intent = Intent(context,Purpose_of_login_Activity::class.java)
                intent.putExtra("ClassesArray",list_of_classes)
                intent.putExtra("faculty_name",Classes[size-1])
                context.startActivity(intent)
            }
        }
    }



    private fun checkInputs() {
        if(etID.text.isNotEmpty()){
            if(etPassword.text.isNotEmpty()){
                login.isEnabled = true
            }
            else
            {
                login.isEnabled = false
            }
        }
        else
        {
            login.isEnabled = false
        }
    }
}
