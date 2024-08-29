package com.synrgy.ch7.ui.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.ch7.R
import com.synrgy.ch7.databinding.ActivityProfileBinding
import com.synrgy.ch7.ui.activities.auth.LoginActivity
import java.io.InputStream

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var pickImageLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the ActivityResultLauncher
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val inputStream: InputStream? = contentResolver.openInputStream(it)
                val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                binding.profileImage.setImageBitmap(bitmap)
            }
        }

        setupClickListeners()
        loadUserProfile()
    }

    private fun setupClickListeners() = with(binding) {
        topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        btnUploadImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        btnUpdate.setOnClickListener {
            updateProfile()
        }

        btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun loadUserProfile() {
        // Load user profile data into EditText fields, for example from SharedPreferences or ViewModel
        // For now, let's use placeholder data
        binding.etUsername.setText("JohnDoe")
        binding.etFullName.setText("John Doe")
        binding.etDateOfBirth.setText("01/01/1990")
        binding.etAddress.setText("123 Main Street")
    }

    private fun updateProfile() {
        val username = binding.etUsername.text.toString()
        val fullName = binding.etFullName.text.toString()
        val dateOfBirth = binding.etDateOfBirth.text.toString()
        val address = binding.etAddress.text.toString()

        // Save the updated profile information
        // For example, save to SharedPreferences or ViewModel
        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
    }

    private fun logout() {
        // Clear user session or token, if any
        // Redirect to login activity
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
