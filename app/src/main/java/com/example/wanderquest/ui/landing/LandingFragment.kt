//package com.example.wanderquest.ui.landing
//
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//import com.example.wanderquest.R
//import com.example.wanderquest.databinding.FragmentSecondBinding
//
//private var _binding: FragmentSecondBinding? = null
//private val binding get() = _binding!!
//
///**
// * A simple [Fragment] subclass as the second destination in the navigation.
// */
//class LandingFragment : Fragment() {
//
//    private var _binding: FragmentSecondBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentSecondBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.buttonSecond.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
//
//        // Initialize the buttonOpenGoogleMaps from the layout
//        binding.buttonOpenGoogleMaps.setOnClickListener {
//            openGoogleMaps()
//        }
//    }
//
//    private fun openGoogleMaps() {
//        // You can open Google Maps in the app or a browser, depending on your preference.
//        // Here, it opens Google Maps in the app if available, or in a browser.
//        val gmmIntentUri = Uri.parse("geo:0,0?q=Google%20Maps")
//        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//        mapIntent.setPackage("com.google.android.apps.maps")
//
//        if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
//            startActivity(mapIntent)
//        } else {
//            // Google Maps app is not installed, open in a browser
//            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com"))
//            startActivity(browserIntent)
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
////
package com.example.wanderquest.ui.landing
import android.os.Bundle
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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanderquest.databinding.ItemTileBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class LandingFragment : Fragment() {

    data class ItemTile(val text: String, val imageResId: Int)

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var tileAdapter: TileAdapter

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

        tileAdapter = TileAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = tileAdapter

        // Add sample tiles to the adapter
        val sampleTiles = listOf(
            ItemTile("Tourist Attraction", R.drawable.restaurant),
            ItemTile("Shopping", R.drawable.shopping2),
            ItemTile("Restaurant", R.drawable.touristattraction2)
        )
        tileAdapter.submitList(sampleTiles)


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

    private class TileViewHolder(private val binding: ItemTileBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemTile: ItemTile) {
            binding.buttonTile.text = itemTile.text
            binding.imageView.setImageResource(itemTile.imageResId)
        }
    }

    private class TileAdapter : ListAdapter<ItemTile, TileViewHolder>(TileDiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TileViewHolder {
            val binding = ItemTileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TileViewHolder(binding)
        }

        override fun onBindViewHolder(holder: TileViewHolder, position: Int) {
            val itemTile = getItem(position)
            holder.bind(itemTile)
        }
    }

    private class TileDiffCallback : DiffUtil.ItemCallback<ItemTile>() {

        override fun areItemsTheSame(oldItem: ItemTile, newItem: ItemTile): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ItemTile, newItem: ItemTile): Boolean {
            return oldItem == newItem
        }
    }
}