package com.example.pmaprojekt

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException

class JobDetails : AppCompatActivity() {

    lateinit var listView: ListView

    var titleArray:ArrayList<String> = ArrayList()
    var locationArray : ArrayList<String> = ArrayList()
    var descriptionArray : ArrayList<String> = ArrayList()
    var urlArray : ArrayList<String> = ArrayList()

    //definice promennych na historii
    val fileName = "MyFavourite.txt"
    val filepath = "MyFileDir"
    var fileContent = ""
    var FavouriteData = ""

    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)                  //tady
        setContentView(R.layout.activity_job_details)
        val Bundle = intent.extras
        val JSON = Bundle?.get("JSON")

       val JSON2 = JSONObject(JSON.toString())
        val job = JSON2.getJSONArray("jobs")
        Log.v("Job", job.toString())



        for(i in 0 until job.length()){
              //  println(job[i])

                val pokus = job.getJSONObject(i)
                val locations = pokus.getString("locations")
                val description = pokus.getString("description")
                val title = pokus.getString("title")
                val url = pokus.getString("url")

                titleArray.add(title)
                locationArray.add(locations)
                descriptionArray.add(description)
                urlArray.add(url)

                Log.v("promenna", title)
                Log.v("promenna", locations)
                Log.v("promenna", description)
               /* println(title)
                println(locations)
                println(description) */

        }
        println(titleArray)         //proc tisknu titlearray

        val myListAdapter = MyListAdapter(this,titleArray,locationArray,descriptionArray)
        listView = findViewById<ListView>(R.id.ListViewID)
        listView.adapter = myListAdapter



        listView.setOnItemClickListener(){adapterView, view, position, id ->
            // val itemAtPos = adapterView.getItemAtPosition(position)
            //val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            // Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(urlArray[adapterView.getItemIdAtPosition(position).toInt()]))
            startActivity(i)
    }

        listView.onItemLongClickListener = AdapterView.OnItemLongClickListener { _, _, position, _ ->
            Toast.makeText(applicationContext,"Add to favourite", Toast.LENGTH_SHORT).show()
            // zde zacina ukladani do historie
            var myExternalFile: File = File(getExternalFilesDir(filepath), fileName)
            var contents = ""
            if(myExternalFile.exists()) {
                if (isExternalStorageReadable()) {
                    contents = myExternalFile.readText()
                    print(contents)
                }
            }

                    var JSON_Array = JSONArray()

                    if(contents != null && contents != ""){
                    JSON_Array =JSONArray(contents)
                    }

                    var nalezeno = false
                    var nalezenoIndex = -1

            for(i in 0 until JSON_Array.length()){
                //  println(job[i])

                val pokus = JSON_Array.getJSONObject(i)
                val locations = pokus.getString("locations")
                val description = pokus.getString("description")
                val title = pokus.getString("title")
                val url = pokus.getString("url")

                if(locations == locationArray[position] &&
                        description == descriptionArray[position] &&
                        title == titleArray[position]&&
                        url == urlArray[position]) {

                    nalezeno = true
                    nalezenoIndex = i
                    break
                }

            }

                    if(nalezeno == false){
                    var JSON_Object = JSONObject()
                    JSON_Object.put("locations", locationArray[position])
                    JSON_Object.put("description", descriptionArray[position])
                    JSON_Object.put("title", titleArray[position])
                    JSON_Object.put("url", urlArray[position])

                    JSON_Array.put(JSON_Object)
                 }

                    else{

                        JSON_Array.remove(nalezenoIndex)
                    }
                    fileContent = JSON_Array.toString()

            try {
                myExternalFile.writeBytes(fileContent.toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()
            }

            true
        }











        // zde zacina ukladani do historie
        /*var myExternalFile: File = File(getExternalFilesDir(filepath), fileName)
        if(myExternalFile.exists()) {
            if (isExternalStorageReadable()) {
                val contents = myExternalFile.readText()
                print(contents)
                fileContent = contents +"\n"+ FavouriteData.toString()
                myExternalFile.writeBytes(fileContent.toByteArray())
            }
        }
        else
        {
            try {
                myExternalFile.writeBytes(FavouriteData.toString().toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }*/
        // zde konci ukladani do historie
}
}