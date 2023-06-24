package com.example.food_delivery_app.Fragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
//import com.example.food_delivery_app.Manifest
import com.example.food_delivery_app.Models.user
import com.example.food_delivery_app.R
import com.example.food_delivery_app.Retrofit.Endpoint
import com.example.food_delivery_app.databinding.FragmentSignInBinding
import com.example.food_delivery_app.databinding.FragmentSignUpBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SignUpFragment : Fragment() {

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private var pickImage = 100
    var imageUri: String = ""
    val requestCode = 400

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
     binding = FragmentSignUpBinding.inflate(inflater, container, false)
        //val bottomNavigationView =
          //  requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        //bottomNavigationView.visibility = View.GONE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                handleActivityResult(data)
            }
        }

     /*   binding.setOnClickListener {
            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            dialog.setContentView(R.layout.camera_dialog)
            dialog.setCancelable(true)
            dialog.show()
            dialog.window?.findViewById<ImageView>(R.id.gallery_image)?.setOnClickListener {
                if (ContextCompat.checkSelfPermission(
                        requireActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    openGallery()
                } else {
                    requestGalleryPermission()
                }
                dialog.cancel()
            }
        }*/

        binding.registerButton.setOnClickListener {
            val email = binding.adresseMailtexte2.text.toString()
            val password = binding.passwordtexte2.text.toString()
            val username = binding.nametexte.text.toString()
            val phoneNumber = binding.numerotexte.text.toString()
            if (email.isEmpty() || password.isEmpty() || username.isEmpty() || phoneNumber.isEmpty() || imageUri.isEmpty()) {
                Toast.makeText(requireContext(), "Error! Something is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            registerUser(email, password, username, phoneNumber, imageUri)
        }

        binding.connecterlink.setOnClickListener {
            view.findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activityResultLauncher.launch(galleryIntent)
    }
/*
    private fun requestGalleryPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            pickImage
        )
    }*/

    private fun registerUser(email: String, password: String, username: String, phoneNumber: String, imageUri: String) {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {
                Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_LONG).show()
            }
        }

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = Endpoint.createEndpoint().register(user(1, username, email, password, phoneNumber, imageUri))

            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!
                    Toast.makeText(requireContext(), "Account created successfully", Toast.LENGTH_SHORT).show()
                    view?.findNavController()?.popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Error!!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleActivityResult(data: Intent?) {
        data?.data?.let { selectedImage ->
            uploadImageToFirebase(selectedImage)
        } ?: run {
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            imageBitmap?.let {
                uploadBitmapToFirebase(imageBitmap)
            }
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri) {
        val storage = FirebaseStorage.getInstance()
        val storageRef: StorageReference = storage.reference

        val filename = "image_${System.currentTimeMillis()}.jpg"
        val imageRef = storageRef.child("images/$filename")
        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                this.imageUri = downloadUri.toString()
                Toast.makeText(requireContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Failed to retrieve download URL", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(requireContext(), "Image upload failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadBitmapToFirebase(bitmap: Bitmap) {
        val storage = FirebaseStorage.getInstance()
        val storageRef: StorageReference = storage.reference

        val filename = "image_${System.currentTimeMillis()}.jpg"
        val imageRef = storageRef.child("images/$filename")

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageData = baos.toByteArray()

        val uploadTask = imageRef.putBytes(imageData)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                this.imageUri = downloadUri.toString()
                Toast.makeText(requireContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Failed to retrieve download URL", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(requireContext(), "Image upload failed", Toast.LENGTH_SHORT).show()
        }
    }

   /* override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }*/

   /* override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }*/
}