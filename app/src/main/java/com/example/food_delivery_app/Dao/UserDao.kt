package com.example.food_delivery_app.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.food_delivery_app.Models.user

@Dao
interface userDao {
    @Query("SELECT * FROM users where email=:email")
    fun getUserByEmail(email:String):List<user>

    @Insert()
    fun insertUser(user: user)

    @Delete()
    fun deleteUser(user: user)

    @Update()
    fun updateUser(user: user)
}