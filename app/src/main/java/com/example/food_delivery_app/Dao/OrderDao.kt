package com.example.food_delivery_app.Dao


import androidx.room.*
import com.example.food_delivery_app.Models.order

@Dao
interface orderDao {
    // get all orders from the database
    @Query("SELECT * FROM orders")
    fun getAllOrders():List<order>

    // get an order from the database
    @Query("SELECT * FROM orders WHERE id=:id")
    fun getOrder(id:Int): order

    // add an order to the database
    @Insert
    fun insertOrder(order: order)

    // delete an order from the database
    @Delete
    fun deleteOrder(order: order)

    // update an order in the database
    @Update
    fun updateOrder(order: order)

    // delete all orders from the database
    @Query("DELETE FROM orders")
    fun deleteAllOrders()

}