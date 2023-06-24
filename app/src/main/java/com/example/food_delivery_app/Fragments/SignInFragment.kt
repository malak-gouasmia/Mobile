package com.example.food_delivery_app.Fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.food_delivery_app.Models.Credentials
import com.example.food_delivery_app.Models.menu
import com.example.food_delivery_app.Models.order
import com.example.food_delivery_app.MyDataBase
import com.example.food_delivery_app.R
import com.example.food_delivery_app.Retrofit.Endpoint
import com.example.food_delivery_app.databinding.FragmentOverviewBinding
import com.example.food_delivery_app.databinding.FragmentSignInBinding
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

//import com.example.food_delivery_app.databinding.FragmentOverviewBinding
class SignInFragment : Fragment() {
    lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSignInBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     //   val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
       // bottomNavigationView.visibility = View.GONE
        val sharedPref = requireActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        binding.loginButton.setOnClickListener{
            val email = binding.adresseMailtexte.text.toString()
            val password = binding.passwordtexte.text.toString()
            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(it.context,"email or password is empty!",Toast.LENGTH_LONG).show();
                return@setOnClickListener
            }
            loginHandler(it,requireActivity(),email,password)
        }
        binding.crErlink.setOnClickListener {
            it.findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Show the bottom navigation bar when leaving the fragment
       // val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        //bottomNavigationView.visibility = View.VISIBLE
    }
   /* override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }
*/
   /* override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }*/

    fun loginHandler(view: View, ctx: Activity, email: String, password: String) {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            ctx.runOnUiThread {
                Toast.makeText(ctx, throwable.message, Toast.LENGTH_LONG).show()
            }
        }

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = Endpoint.createEndpoint().login(Credentials(email, password))

            withContext(Dispatchers.Main) {
                println(response.body())

                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!

                    val token = FirebaseMessaging.getInstance().token.await()
                    println("token: $token")

                    val tokenResponse = Endpoint.createEndpoint().createToken(data._id.toString(), mapOf("token" to token))
                    if (!tokenResponse.isSuccessful) {
                        println("Error creating token")
                        return@withContext
                    }

                    val sharedPref = ctx.getSharedPreferences("my_preferences", Context.MODE_PRIVATE).edit()
                    sharedPref.putString("email", data.email)
                    sharedPref.putBoolean("isLogged", true)
                    sharedPref.putString("userId", data._id.toString())
                    sharedPref.putString("phone", data.phone)
                    sharedPref.putString("username", data.username)
                    sharedPref.putString("profilePic", data.profilePic)
                    sharedPref.apply()

                    Toast.makeText(ctx, "Logged in successfully", Toast.LENGTH_SHORT).show()
                    view.findNavController().popBackStack()
                } else {
                    Toast.makeText(ctx, "Error!!!", Toast.LENGTH_SHORT).show()
                    return@withContext
                }
            }
        }
    }

}