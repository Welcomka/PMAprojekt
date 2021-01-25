package com.example.pmaprojekt.ui.My_jobs

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pmaprojekt.MyListAdapter
import com.example.pmaprojekt.R
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException


class MyJobsFragment : Fragment() {

    private lateinit var myJobsViewModel: MyJobsViewModel

    lateinit var listView: ListView

    var titleArray:ArrayList<String> = ArrayList()
    var locationArray : ArrayList<String> = ArrayList()
    var descriptionArray : ArrayList<String> = ArrayList()
    var urlArray : ArrayList<String> = ArrayList()
    lateinit var myListAdapter : MyListAdapter

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
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        myJobsViewModel =
                ViewModelProvider(this).get(MyJobsViewModel::class.java)


        val root = inflater.inflate(R.layout.fragment_myjobs, container, false)

        /*for(i in 0 until job.length()){
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
        println(titleArray)*/

        var myExternalFile: File = File(this.activity?.getExternalFilesDir(filepath), fileName)
        var contents = ""
        if(myExternalFile.exists()) {
            if (isExternalStorageReadable()) {
                contents = myExternalFile.readText()
                print(contents)
            }
        }

        var JSON_Array = JSONArray()

        if(contents != null && contents != ""){
            JSON_Array = JSONArray(contents)
        }


        for(i in 0 until JSON_Array.length()){

            val pokus = JSON_Array.getJSONObject(i)
            val locations = pokus.getString("locations")
            val description = pokus.getString("description")
            val title = pokus.getString("title")
            val url = pokus.getString("url")

            locationArray.add(locations)
            descriptionArray.add(description)
            titleArray.add(title)
            urlArray.add(url)

        }

        myListAdapter = MyListAdapter(this.activity as Activity,titleArray,locationArray,descriptionArray)
        listView = root.findViewById<ListView>(R.id.ListViewID2)
        listView.adapter = myListAdapter



        listView.setOnItemClickListener(){adapterView, view, position, id ->
            // val itemAtPos = adapterView.getItemAtPosition(position)
            //val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            // Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(urlArray[adapterView.getItemIdAtPosition(position).toInt()]))
            startActivity(i)
        }

        listView.onItemLongClickListener = AdapterView.OnItemLongClickListener { _, _, position, _ ->
            Toast.makeText(this.context,"Remove from favourite", Toast.LENGTH_SHORT).show()
            // zde zacina ukladani do historie
            var myExternalFile: File = File(this.activity?.getExternalFilesDir(filepath), fileName)
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

            // procházení zda už tam prvek je
            for(i in 0 until JSON_Array.length()){

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

            //mazání
            if(nalezeno == true){
                JSON_Array.remove(nalezenoIndex)
                fileContent = JSON_Array.toString()

                try {
                    myExternalFile.writeBytes(fileContent.toByteArray())
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                locationArray.removeAt(nalezenoIndex)
                descriptionArray.removeAt(nalezenoIndex)
                titleArray.removeAt(nalezenoIndex)
                urlArray.removeAt(nalezenoIndex)
                myListAdapter.notifyDataSetChanged()
            }

            true
        }

        /*val textView: TextView = root.findViewById(R.id.text_dashboard)
        myJobsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/



       /* val fileName = "MyFavourite.txt"
        val filepath = "MyFileDir"

        var myExternalFile: File = File(activity?.getExternalFilesDir(filepath), fileName)
        if (myExternalFile.exists()) {
            if (isExternalStorageReadable()) {
                val contents = myExternalFile.readText()
                print(contents)
                promenna.setText(contents)
            }
        }           CTENI ZE SOUBORU MyFavourite a zobrazeni do lib. promenne*/
        return root
    }
}