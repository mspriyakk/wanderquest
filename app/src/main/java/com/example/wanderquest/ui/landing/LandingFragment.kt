package com.example.wanderquest.ui.landing

import android.icu.util.BuddhistCalendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.wanderquest.R
import com.example.wanderquest.databinding.FragmentSecondBinding
import com.example.wanderquest.network.DestinationApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class LandingFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }
    //new editing test here Chloe

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            //val budget = binding.budgetInput.text.toString()
            val budget = "500"
            //val travelStyle = binding.travelStyleInput.text.toString()
            val travelStyle = "outdoor"
            fetchRecommendations(budget, travelStyle)
        }
    }
    //need ui design for get recommendations (destinations)
    private fun fetchRecommendations(budget: String, travelStyle: String) {
        val query = constructQuery(budget, travelStyle)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = DestinationApi.retrofitService.getDestination(query, getYourApiKey())
                withContext(Dispatchers.Main) {
                    val action = LandingFragmentDirections.actionSecondFragmentToFirstFragment()
                    findNavController().navigate(action)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Failed to fetch data: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    //ui design for unlock and lock destinations (from the above destination)

    private fun constructQuery(budget: String, travelStyle: String): String {
        // Construct and return the API query
        return "location = New York& budget= $budget&style=$travelStyle"
    }

    private fun getYourApiKey(): String {
        // Implement secure retrieval of API key
        return ""//BuildConfig.API_KEY
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}