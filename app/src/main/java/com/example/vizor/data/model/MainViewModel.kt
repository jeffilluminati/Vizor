package com.example.vizor.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class MainViewModel: ViewModel() {
    private var uri: MutableLiveData<String> = MutableLiveData<String>("")

    companion object {
        var currentUser: User? = null

        var navController: NavController? = null

    }

    public fun getUri(): String{
        Log.i("g", uri.value.toString())
        return uri.value.toString()
    }

    public fun setUri(uriNew: String){
        uri.value = uriNew
        Log.i("t", uri.value.toString())
    }

}