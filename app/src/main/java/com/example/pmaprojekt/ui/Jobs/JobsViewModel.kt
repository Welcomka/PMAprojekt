package com.example.pmaprojekt.ui.Jobs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JobsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is job"
    }
    val text: LiveData<String> = _text
}