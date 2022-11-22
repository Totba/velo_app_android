package com.example.appli_velo.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appli_velo.R
import com.example.appli_velo.adapter.ParkingAdapter
import com.example.appli_velo.api.ParkingApi
import com.example.appli_velo.api.RetrofitHelper
import com.example.appli_velo.databinding.FragmentNotificationsBinding
import com.example.appli_velo.model.Parking
import com.example.appli_velo.model.allParkings
import com.example.appli_velo.ui.home.MapsActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val parkingLoadingProgresBar = binding.parkingLoadingProgresBar
        val showAllParkingBtn = binding.showAllParkingBtn

        showAllParkingBtn.setOnClickListener { it ->
            val intent = Intent(it.context, MapsActivity::class.java)
            allParkings = notificationsViewModel.parkings.value
            it.context.startActivity(intent)
        }

        val recyclerView: RecyclerView = binding.recyclerViewParking
        notificationsViewModel.parkings.observe(viewLifecycleOwner, Observer {
            val adapter : ParkingAdapter = ParkingAdapter(it, requireContext())
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        })

        val parkingApi = RetrofitHelper().getInstance().create(ParkingApi::class.java)
        GlobalScope.launch {
            val result = parkingApi.getParkings();
            notificationsViewModel.parkings.postValue(result.body())
            parkingLoadingProgresBar.setVisibility(View.INVISIBLE)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}