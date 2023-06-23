package com.example.food_delivery_app.Fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_delivery_app.Adapters.restaurantAdapter
import com.example.food_delivery_app.R
import com.example.food_delivery_app.databinding.FragmentListeRestaurantBinding
import com.example.food_delivery_app.Models.restaurant
import com.example.food_delivery_app.Retrofit.Endpoint
import kotlinx.coroutines.*
import java.net.URL
import java.sql.Time

class liste_restaurant: Fragment() {
    lateinit var binding: FragmentListeRestaurantBinding
   // private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListeRestaurantBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        loadData(binding.recyclerView,requireActivity());
       // loadData();
        return view

    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.adapter = restaurantAdapter(requireActivity(),loadData())
    }*/

   /* override fun onDestroyView() {
        super.onDestroyView()
        binding = null;
    }*/

    //fun loadData(recyclerView: androidx.recyclerview.widget.RecyclerView,ctx: Activity):List<restaurant>
    /* fun loadData(recyclerView: androidx.recyclerview.widget.RecyclerView,ctx: Activity) {
      val data = mutableListOf<restaurant>()
   data.add(
          restaurant(1,
              "Restaurant 1",
              "Cuisine africaine",
              R.drawable.image,
              "Alger",
              130f,
              120f,
              "cuisne algeriene ",
              //
              "04343442",
              3.0f,
              "jm_gouasmia@esi.dz",
              "https://www.google.fr/maps/place/Birkhadem/@36.7176371,3.0280608,14z/data=!3m1!4b1!4m6!3m5!1s0x128fada4576043ff:0xe4fb52ae50139dd1!8m2!3d36.7160675!4d3.0496695!16s%2Fm%2F03wdfrs?entry=ttu",
              "https://web.facebook.com/?_rdc=1&_rdr",
              Time(4,4,4),
              Time(4,4,4),

            )

      )
        data.add(
            restaurant(2,
                "Restaurant 2",
                "Cuisine type 2 ",
                R.drawable.image,
                "tizi",
                140f,
                120f,
                "type xxx ",
                //
                "079999999999",
                3.0f,
                "jm_touhar@esi.dz",
                "https://www.google.fr/maps/place/Birkhadem/@36.7176371,3.0280608,14z/data=!3m1!4b1!4m6!3m5!1s0x128fada4576043ff:0xe4fb52ae50139dd1!8m2!3d36.7160675!4d3.0496695!16s%2Fm%2F03wdfrs?entry=ttu",
                "https://web.facebook.com/?_rdc=1&_rdr",
                Time(6,4,4),
                Time(4,4,4),

                )

        )

         recyclerView.adapter = restaurantAdapter(ctx,data)
     // return data
  }}*/
   /* fun loadData(recyclerView: androidx.recyclerview.widget.RecyclerView,ctx: Activity) {
        var data = mutableListOf<restaurant>()
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            ctx.runOnUiThread {
                Toast.makeText(ctx, throwable.message, Toast.LENGTH_LONG).show()
            }

        }
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = Endpoint.createEndpoint().getAllRestaurant()
            withContext(Dispatchers.Main) {
                println(response.body())
                if (response.isSuccessful && response.body() != null) {
                    data = response.body()!!.toMutableList()
                    recyclerView.adapter = restaurantAdapter(ctx, data)
                } else {
                    Toast.makeText(ctx, "Error detected", Toast.LENGTH_SHORT).show()
                }
            }
        }

    } */

//////////////

    fun loadData(recyclerView: androidx.recyclerview.widget.RecyclerView, ctx: Activity) {
        var data = mutableListOf<restaurant>()

        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            ctx.runOnUiThread {
                Toast.makeText(ctx, throwable.message, Toast.LENGTH_LONG).show()
            }
        }

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            try {
                val response = Endpoint.createEndpoint().getAllRestaurants()

                if (response.isSuccessful) {
                    val restaurantList = response.body()
                    if (restaurantList != null) {
                        println("Ressssssssssssssssssssssssssssssssssssss")
                        //data.addAll(restaurantList)
                        data = restaurantList.toMutableList()
                        withContext(Dispatchers.Main) {
                            recyclerView.adapter = restaurantAdapter(ctx, data)
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(ctx, "Failed to fetch restaurants", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(ctx, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}


