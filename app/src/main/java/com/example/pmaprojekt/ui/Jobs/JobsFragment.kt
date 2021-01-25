package com.example.pmaprojekt.ui.Jobs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pmaprojekt.GetJSON
import com.example.pmaprojekt.JobDetails
import com.example.pmaprojekt.R
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.uiThread

class JobsFragment : Fragment() {

    private lateinit var jobsViewModel: JobsViewModel


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        jobsViewModel =
                ViewModelProvider(this).get(JobsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_jobs, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_home)
        jobsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        val inputJob = root.findViewById<EditText>(R.id.editTextTextPersonName5)
        val inputLocation = root.findViewById<EditText>(R.id.editTextTextPersonName6)
        val RadButtAll = root.findViewById<RadioButton>(R.id.radioButton5)
        val RadButtFT = root.findViewById<RadioButton>(R.id.radioButton8)
        val RadButtPT = root.findViewById<RadioButton>(R.id.radioButton9)
        //val RadButtChecked = RadButtAll.isChecked()
        var promenna =""

        val Button = root.findViewById<Button>(R.id.button)
        val radioGroup = root.findViewById<RadioGroup>(R.id.radioGroup)

        radioGroup.setOnCheckedChangeListener { radioGroup, optionId ->
            run {
                when (optionId) {
                    R.id.radioButton8 -> {
                        promenna = "f"
                    }
                    R.id.radioButton9 -> {
                        promenna = "p"
                        // do something when radio button 2 is selected
                    }
                    // add more cases here to handle other buttons in the your RadioGroup
                    R.id.radioButton5 -> {
                        promenna = ""
                        // do something when radio button 2 is selected
                    }
                }
            }
        }

        Button.setOnClickListener{

            Log.v("Hodnoty", inputJob.text.toString().toLowerCase())
            Log.v("Hodnoty", inputLocation.text.toString().toLowerCase())
            val url = "http://public.api.careerjet.net/search?locale_code=cs_CZ&pagesize=&contractperiod="+promenna+"&sort=date&keywords="+inputJob.text.toString().toLowerCase()+"&page=1&location="+inputLocation.text.toString().toLowerCase()+"&user_agent=Mozilla%2F5.0&user_ip=1.192.0.1&fbclid=IwAR2ge23SZWhWsqtu1WVWeu4-EpoNrXh1Zn_RvvZhnkAfPtXPi4FIE8Lwopo"
            Log.v("URL", url)
            doAsync {
                val JSON = GetJSON(url).Get_JSON()
                Log.v("JSON", JSON)
                if (JSON == "ErrorLocation"){
                    uiThread{
                        toast("Location not found")
                    }
                }
                else if (JSON == "ErrorJob"){
                    uiThread{
                        toast("Job not found")
                    }
                }
                else{
                    val jobDetailsRedirect = Intent(context, JobDetails :: class.java)
                    jobDetailsRedirect.putExtra("JSON", JSON)
                    startActivity(jobDetailsRedirect)
                }
            }


        }

        return root
    }
}