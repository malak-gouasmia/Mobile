
package com.example.food_delivery_app.Adapters
import android.content.Context
import android.os.Bundle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController

import androidx.recyclerview.widget.RecyclerView
import com.example.food_delivery_app.R
import com.example.food_delivery_app.databinding.MenuLayouatBinding
import com.example.food_delivery_app.Models.menu

/*class menuAdapter (val ctx:Context, val data:List<menu>):
    RecyclerView.Adapter<menuAdapter.MyViewHolder2>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder2 {
        return MyViewHolder2(
            MenuLayouatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }*/
class menuAdapter (val ctx:Context, val data:List<menu>):
    RecyclerView.Adapter<menuAdapter.MyViewHolder2>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder2 {
        return MyViewHolder2(
            MenuLayouatBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }





    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder2, position: Int) {
        holder.binding.apply {

            Name.text = data[position].name

        //  imageView.setImageResource(data[position].image)
         //
          prix.text=data[position].price.toString()
            typeMenu.text=data[position].categorie.toString()

       /*   imageView.setImageResource(data[position].image)*/
            typeMenu.text=data[position].categorie
          prix.text=data[position].price.toString()


        }

        holder.itemView.setOnClickListener {
            val bundle= Bundle()
            bundle.putSerializable("menu",data[position])

         //   bundle.putSerializable("menu",data[position])
            it.findNavController().navigate(R.id.action_liste_menu_to_overviewFragment,bundle)
        }







    }



    class MyViewHolder2(val binding: MenuLayouatBinding) : RecyclerView.ViewHolder(binding.root)

}



