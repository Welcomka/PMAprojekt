package com.example.pmaprojekt.ui.My_jobs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyJobsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is my job"
    }
    val text: LiveData<String> = _text
}