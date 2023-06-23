package com.example.food_delivery_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.example.food_delivery_app.Activities.LoginActivity
import com.example.food_delivery_app.Activities.RegisterActivity
import com.example.food_delivery_app.databinding.FragmentLoginBinding


class loginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLoginBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = requireActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        //binding.login.setOnClickListener{
        //val email = binding.emailInput.text.toString()
        //val password = binding.passwordInput.text.toString()
        //if(email.isEmpty() || password.isEmpty()){
        //Toast.makeText(it.context,"email or password is empty!",Toast.LENGTH_LONG).show();
        //return@setOnClickListener
        //}
        //if(email != sharedPref.getString("email", "") || password != sharedPref.getString("password", "")){
        //Toast.makeText(it.context,"email or password is incorrect!",Toast.LENGTH_LONG).show();
        //return@setOnClickListener
        //}
        //sharedPref.edit().putBoolean("isLogged", true).commit()
        //Toast.makeText(it.context,"user logged in!",Toast.LENGTH_LONG).show();
        // navigate back to previous fragment
        //it.findNavController().popBackStack()
        //}
        binding.register.setOnClickListener {
            sharedPref.edit().putString("email", "test@gmail.com").apply()
            sharedPref.edit().putString("password", "test").apply()
            Toast.makeText(it.context,"user created!",Toast.LENGTH_LONG).show()
        }
        val loginBtn = view.findViewById<Button>(R.id.login)
        loginBtn.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }
        val registerBtn = view.findViewById<Button>(R.id.register)
        registerBtn.setOnClickListener {
            val intent = Intent(requireActivity(), RegisterActivity::class.java)
            startActivity(intent)
        }
    }

}