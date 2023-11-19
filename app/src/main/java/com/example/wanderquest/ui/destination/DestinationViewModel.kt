package com.example.wanderquest.ui.destination

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wanderquest.network.DestinationApi
import kotlinx.coroutines.launch

class DestinationViewModel: ViewModel () {
    //write get
    fun getDestination(){
        viewModelScope.launch {
            val result = DestinationApi.retrofitService.getDestination("restaurants in New York", "AIzaSyCRPyR0FILxgbwgLDfgRMzU3zohQnnKaRE");
            Log.d("hooray ", result.toString())


            //parse result  by place name, image, category, price, rating , new properties locked (boolean)
        }
    }
}


