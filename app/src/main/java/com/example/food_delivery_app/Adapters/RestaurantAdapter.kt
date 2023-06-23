package com.example.food_delivery_app.Adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_delivery_app.*
import com.example.food_delivery_app.Models.restaurant
import com.example.food_delivery_app.databinding.RestaurantLayoutBinding


class restaurantAdapter(val ctx:Context, val data:List<restaurant>):
    RecyclerView.Adapter<restaurantAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RestaurantLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            Name.text = data[position].name
            type.text=data[position].cuisineType
            address.text=data[position].locationAddress
            Glide.with(ctx)
                .load(data[position].logo)
                .into(imageView)
           // imageView.setImageResource(data[position].logo)
            ratingBar.rating=data[position].averageRating
            mail.setOnClickListener {
                sendEmail(ctx,data[position].email)
            }
            fb.setOnClickListener{
                openPage(ctx,"fb://page/218641444910278","https://zh-cn.facebook.com/RenaultRomania/photos/%22",listOf<String>("com.facebook.katana" , "com.facebook.lite"));
            }
            localisation.setOnClickListener {
              mymapShow(ctx,data[position].locationMapLat,data[position].locationMaplong)
           }
           /* localisation.setOnClickListener {
                mapShow(ctx,data[position].locationMaplong)
            }*/


        }
        holder.itemView.setOnClickListener {
            it.findNavController().navigate(R.id.action_liste_restaurant_to_liste_menu)

        }

        holder.binding.phone.setOnClickListener{

            openPhone(ctx,data[position].phoneNumber)

        }

    }



    class MyViewHolder(val binding: RestaurantLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}



