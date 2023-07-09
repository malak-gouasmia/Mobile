package com.example.food_delivery_app.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_delivery_app.MyDataBase

//import com.example.food_delivery_app.databinding.OrderLayoutBinding

import com.example.food_delivery_app.databinding.OrderLayoutBinding
import com.example.food_delivery_app.Models.order

class orderAdapter(val activity:FragmentActivity, val list: List<order>) :
    RecyclerView.Adapter<orderAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: OrderLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            OrderLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            menuTitle.text= list[position].nom;
            price.text= list[position].price.toString();

            //menuImage.setImageResource(list[position].image);
            quantity.text= list[position].quantity.toString();
        }
        holder.binding.delete.setOnClickListener {
            MyDataBase.getInstance(activity)?.getOrderDao()?.deleteOrder(list[position]);
            notifyDataSetChanged()
        }
    }
}