package com.example.food_delivery_app


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_delivery_app.Adapters.orderAdapter
import com.example.food_delivery_app.Models.order


import com.example.food_delivery_app.databinding.CheckoutBinding


class checkoutFragment : Fragment() {
    lateinit var binding: CheckoutBinding
    private var userLocation: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= CheckoutBinding.inflate(layoutInflater)
        val view = binding.root
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.adapter = orderAdapter(requireActivity(),loadData())
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val total = loadData().sumOf { it.price*it.quantity }
        binding.amount.text = " ${total} DA"
        val sharedPref= requireActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val isLogged= sharedPref.getBoolean("isLogged", false)
        binding.order.setOnClickListener{
            if(isLogged){
                // Retrieve user's location
                val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
                if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
                } else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, object :
                        LocationListener {
                        override fun onLocationChanged(location: Location) {
                            userLocation = "${location.latitude}, ${location.longitude}"
                            // navigate
                            val bundle = Bundle()
                            println(userLocation)
                            bundle.putString("location", userLocation)
                            locationManager.removeUpdates(this) // stop listening for location updates
                            it.findNavController().navigate(R.id.action_checkoutFragment_to_validationFragment2,bundle)
                        }

                        override fun onProviderDisabled(provider: String) {}
                        override fun onProviderEnabled(provider: String) {}
                        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
                    })
                }
            } else {
                it.findNavController().navigate(R.id.action_checkoutFragment_to_loginFragment)
            }
        }
    }
    fun loadData():List<order> {
        val data= MyDataBase.getInstance(requireActivity())?.getOrderDao()?.getAllOrders()
        return data?: emptyList()
    }

}