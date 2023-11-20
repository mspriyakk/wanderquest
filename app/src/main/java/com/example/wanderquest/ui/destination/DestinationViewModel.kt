package com.example.wanderquest.ui.destination

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wanderquest.data.Response
import com.example.wanderquest.network.DestinationApi
import kotlinx.coroutines.launch

class DestinationViewModel: ViewModel () {
    fun getDestination(){
        viewModelScope.launch {
            //parse result  by place name, image, category, price, rating , new properties locked (boolean)

            try{
                val response = DestinationApi.retrofitService.getDestination(
                    "restaurants in New York",
                    "AIzaSyBV0idDDgajLB0X69oYvq8bPnr-uO092gw"
                )
                Log.d("hooray ", response.toString())
                handleDestinationResponse(response)
            } catch (e: Exception) {

            }
        }
    }
    private fun handleDestinationResponse(response: Response){
        val destinations = response.results
        Log.d("API results ", destinations.toString())
    }
}


