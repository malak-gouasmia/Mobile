package com.example.food_delivery_app.Fragments

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_delivery_app.Adapters.menuAdapter
import com.example.food_delivery_app.R
import com.example.food_delivery_app.databinding.FragmentListeMenuBinding
import com.example.food_delivery_app.Models.menu
import com.example.food_delivery_app.Retrofit.Endpoint
import kotlinx.coroutines.*

class liste_menu: Fragment() {
    lateinit var binding: FragmentListeMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentListeMenuBinding.inflate(layoutInflater)
        val view = binding.root

        var idRestaurant = arguments?.getString("id")?: "1"
        println("idrestaur;;;;")
        println(idRestaurant)
        println("idrestaur;;;;")
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        loadData(idRestaurant.toString(),binding.recyclerView,requireActivity());
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        println("menuuuuuuu************************************************************************")
        super.onViewCreated(view, savedInstanceState)
        println("1********************************************************************************")
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.setContentView(R.layout.review_dialog)
        dialog.setCancelable(true)
        val sharedPref= requireActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val isLogged= sharedPref.getBoolean("isLogged", false)
        binding.recyclerView.setOnClickListener{
            if(isLogged){
                dialog.show()
            } else {
                it.findNavController().navigate(R.id.action_checkoutFragment_to_loginFragment)
            }
        }
    }

    /* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
         var idRestaurant = arguments?.getInt("idRestaurant")?: 0
         binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
         binding.recyclerView.adapter = menuAdapter(requireActivity(),loadData(idRestaurant))
        // binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
         //binding.recyclerView.adapter = menuAdapter(requireActivity(),loadData())
     }*/

    // override fun onDestroyView() {
    //   super.onDestroyView()
    // _binding = null
    //}

    fun loadData(id: String, recyclerView: androidx.recyclerview.widget.RecyclerView,ctx: Activity){
        println("1********************************************************************************")

        var data= mutableListOf<menu>()
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            ctx.runOnUiThread {
                println("2********************************************************************************")

                println(throwable.message)
                Toast.makeText(ctx, throwable.message, Toast.LENGTH_LONG).show()
            }

        }
        CoroutineScope(Dispatchers.IO+ exceptionHandler).launch {
            val response = Endpoint.createEndpoint().getRestaurantMenuById(id)


            println("Before")
            println("*********************************************************************************")
            println(response)
            println("after")
            withContext(Dispatchers.Main) {
                println(response.body())
                if (response.isSuccessful && response.body() != null) {
                    data= response.body()!!.toMutableList()
                    recyclerView.adapter = menuAdapter(ctx,data)
                } else {
                    println(id)
                    Toast.makeText(ctx, "Error!!!", Toast.LENGTH_SHORT).show()
                }
            }
        }



    }

}