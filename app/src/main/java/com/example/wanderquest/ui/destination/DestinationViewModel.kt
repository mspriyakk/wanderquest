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
            try{
            val response = DestinationApi.retrofitService.getDestination(
                "restaurants in New York",
                ""
            )
                handleDestinationResponse(response)
        } catch (e: Exception) {

        }
    }
}
    private fun handleDestinationResponse(response: Response){
        val destinations = response.results

    }
}


