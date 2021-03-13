package com.example.vizor.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private lateinit var uri: MutableLiveData<String>
    companion object {
        var currentUser: User? = null

    }

    init {
        uri = MutableLiveData<String>("")
    }


    public fun getUri(): String{
        return uri.value.toString()
        Log.i("g", uri.value.toString())
    }

    public fun setUri(uriNew: String){
        uri.value = uriNew
        Log.i("t", uri.value.toString())
    }

}