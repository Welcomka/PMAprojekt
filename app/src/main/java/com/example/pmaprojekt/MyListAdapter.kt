package com.example.pmaprojekt
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
class MyListAdapter(private val context: Activity, private val title: ArrayList<String>, private val location: ArrayList<String>, private val description: ArrayList<String>)
    : ArrayAdapter<String>(context, R.layout.custom_list, title) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater                                                   //tady
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView
        val locationText = rowView.findViewById(R.id.location) as TextView
        val descriptionText = rowView.findViewById(R.id.description) as TextView

        titleText.text = title[position]                                                       //tady
        locationText.text = location[position]
        descriptionText.text = description[position]

        return rowView
    }
}