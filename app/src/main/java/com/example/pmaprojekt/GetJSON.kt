package com.example.pmaprojekt

import android.util.Log
import java.lang.Exception
import java.net.URL
import org.jetbrains.anko.uiThread

class GetJSON(private val url: String) {
    fun Get_JSON() : String {
        try {
            val GetJSON = URL(url).readText()
            if(GetJSON.contains("solveLocations"))
            {
                return "ErrorLocation"
            }
            else if(GetJSON.contains("hits"+'"'+":0")){
                return "ErrorJob"
            }
            return GetJSON;
        }
        catch (ex:Exception){
            Log.v("Chyba", ex.toString())
            return "Error";
        }

    }
}