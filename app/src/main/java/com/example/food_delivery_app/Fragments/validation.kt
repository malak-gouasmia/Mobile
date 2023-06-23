package com.example.food_delivery_app


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController

import com.example.food_delivery_app.databinding.FragmentValidationBinding


class validationFragment : Fragment() {
    lateinit var binding: FragmentValidationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentValidationBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data= arguments?.getString("location")?:""
        binding.location.text= data
        binding.button2.setOnClickListener {
            MyDataBase.getInstance(requireActivity())?.getOrderDao()?.deleteAllOrders()
            val sharedPref= requireActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
            sharedPref.edit().putInt("id",0).apply()
            it.findNavController().navigate(R.id.action_validationFragment2_to_liste_restaurant)
        }
    }

}