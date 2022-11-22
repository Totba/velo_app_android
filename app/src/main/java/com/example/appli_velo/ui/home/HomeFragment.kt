package com.example.appli_velo.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appli_velo.adapter.StationAdapter
import com.example.appli_velo.api.RetrofitHelper
import com.example.appli_velo.api.StationApi
import com.example.appli_velo.databinding.FragmentHomeBinding
import com.example.appli_velo.model.Station
import com.example.appli_velo.model.allStations
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val stationApi = RetrofitHelper().getInstance().create(StationApi::class.java)
    var bikeLoadingProgresBar : ProgressBar? = null

    fun changeStatusStation(station : Station) {
        bikeLoadingProgresBar!!.visibility = (View.VISIBLE)
        GlobalScope.launch {
            val result = stationApi.changeStatus(station.id)

            if(result.code() == 200) {
                loadStations()
                bikeLoadingProgresBar!!.visibility = (View.INVISIBLE)
            }
        }
    }

    fun loadStations() {
        GlobalScope.launch {
            val result = stationApi.getStations()
            homeViewModel.stations.postValue(result.body())
            bikeLoadingProgresBar!!.setVisibility(View.INVISIBLE);
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        bikeLoadingProgresBar = binding.bikeLoadingProgresBar
        val showAllStationBtn = binding.showAllStationBtn

        showAllStationBtn.setOnClickListener { it ->
            val intent = Intent(it.context, MapsActivity::class.java)
            allStations = homeViewModel.stations.value
            it.context.startActivity(intent)
        }

        val recyclerView: RecyclerView = binding.recyclerView
        homeViewModel.stations.observe(viewLifecycleOwner, {
            val adapter = StationAdapter(it, requireContext(), this)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        })
        loadStations()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}