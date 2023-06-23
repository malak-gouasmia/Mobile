package com.example.food_delivery_app


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.food_delivery_app.Dao.orderDao
import com.example.food_delivery_app.Models.order

@Database(entities = [order::class],version = 1)
abstract class MyDataBase: RoomDatabase() {
    abstract fun getOrderDao(): orderDao

    companion object {
        private var INSTANCE: MyDataBase? = null

        fun getInstance(context: Context): MyDataBase? {
            if (INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(context,MyDataBase::class.java,
                        "users_db").allowMainThreadQueries().build() }

            return INSTANCE
        }
    }
}