package com.example.food_delivery_app


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.food_delivery_app.Models.menu
import com.example.food_delivery_app.Models.order
import com.example.food_delivery_app.databinding.FragmentOverviewBinding


class overviewFragment : Fragment() {
    lateinit var binding: FragmentOverviewBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentOverviewBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arg= arguments?.getSerializable("menu") as menu
        binding.apply {
            name.text= arg.name;
            price.text= arg.price.toString();
            //  imageView3.setImageResource(arg.image);
            //  description.text= arg.desciption;
            // delivery.text= arg.deliveryTime;
            //  nbRate.text= arg.nbRate.toString();
            // size.text= arg.size;
        }
        binding.addCart.setOnClickListener{
            val sharedPref= requireActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
            val idRestaurant= sharedPref.getInt("idRestaurant", 0)
            if(idRestaurant==0){
                idRestaurant==arg.id
                sharedPref.edit().putInt("idRestaurant", arg.id).apply()
            }else{
                val data=MyDataBase.getInstance(requireActivity())?.getOrderDao()?.getAllOrders()
                if(data!!.isEmpty()){
                    idRestaurant==arg.restaurant_id
                    sharedPref.edit().putInt("idRestaurant", arg.id).apply()
                }
            }
            if(idRestaurant==arg.restaurant_id){
                val qnt= binding.Amount.text.toString()
                if(qnt.isEmpty()){
                    Toast.makeText(it.context, "Please enter a quantity", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                MyDataBase.getInstance(requireActivity())?.getOrderDao()?.insertOrder(
                    order(
                        idRestaurant=arg.restaurant_id,
                        idMenu = arg.id,
                        nom = arg.name,
                        price = arg.price,
                        image="arg.name",
                        // image = arg.image,
                        quantity = binding.Amount.text.toString().toInt()
                    )
                );
                Toast.makeText(it.context, "Added to cart", Toast.LENGTH_SHORT).show()
            }else{
                val builder = AlertDialog.Builder(requireActivity())
                builder.setTitle("Are you sure?")
                builder.setMessage("You already have an order in progress. Adding a new order from different restaurant will discard your current cart. Do you want to continue?")
                builder.setPositiveButton("Yes") { dialog, which ->
                    // clear the order table and update the restaurant ID in the ViewModel
                    MyDataBase.getInstance(requireActivity())?.getOrderDao()?.deleteAllOrders()
                    sharedPref.edit().putInt("idRestaurant", arg.restaurant_id).apply()
                    // add the new order to the order table
                    val data = MyDataBase.getInstance(requireActivity())?.getOrderDao()?.insertOrder(
                        order(
                            1,
                            arg.restaurant_id,
                            arg.id,
                            arg.name,
                            arg.price,
                          //  arg.description,
                            arg.image,
                            binding.Amount.text.toString().toInt()
                        )
                    )
                    Toast.makeText(it.context, "Added to cart", Toast.LENGTH_SHORT).show()
                }
                builder.setNegativeButton("No") { dialog, which ->
                    // do nothing and close the dialog
                    dialog.dismiss()
                }
                builder.show()
            }
        }
        binding.checkout.setOnClickListener{
            it.findNavController().navigate(R.id.action_overviewFragment_to_checkoutFragment)
        }
    }
}