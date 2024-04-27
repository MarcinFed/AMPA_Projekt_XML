package com.example.a5_marcin_fedorowicz.Settings

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.recreate
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.a5_marcin_fedorowicz.R
import com.example.a5_marcin_fedorowicz.databinding.FragmentHomeScreenSettingsBinding
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class HomeScreenSettingsFragment : Fragment() {

    private lateinit var binding: FragmentHomeScreenSettingsBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeScreenSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val invitationText = sharedPreferences.getString("invitationHint", null)
        val nickText = sharedPreferences.getString("nickHint", null)
        val profilePicture = sharedPreferences.getString("profilePicturePath", null)

        if (!invitationText.isNullOrEmpty()) {
            binding.invitationInput.setText(invitationText)
        } else {
            binding.invitationInput.setHint(R.string.invitation_hint)
        }
        if (!nickText.isNullOrEmpty()) {
            binding.nickInput.setText(nickText)
        } else {
            binding.nickInput.setHint(R.string.nick_hint)
        }

        binding.saveButton.setOnClickListener {
            saveData()
        }

        binding.resetButton.setOnClickListener {
            resetStartScreen()
        }
    }

    private fun saveData() {
        val invitationText =
            binding.invitationInput.text.toString().takeIf { it.isNotBlank() } ?: null
        val nickText = binding.nickInput.text.toString().takeIf { it.isNotBlank() } ?: null

        with(sharedPreferences.edit()) {
            putString("invitationHint", invitationText)
            putString("nickHint", nickText)
            apply()
        }
        //saveImageToSharedPreferences(SELECTED_IMAGE?.toString())
        Toast.makeText(requireContext(), "Data saved", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage = data.data
            val contentResolver = requireContext().contentResolver

            val takeFlags: Int =
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            contentResolver.takePersistableUriPermission(selectedImage!!, takeFlags)

            //saveImageToSharedPreferences(selectedImage.toString())
        }
    }

//    private fun saveImageToSharedPreferences(imagePath: String?) {
//        with(sharedPreferences.edit()) {
//            putString("profilePicturePath", imagePath)
//            apply()
//        }
//    }

    companion object {
        fun newInstance() = HomeScreenSettingsFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }

    private fun resetStartScreen() {
        with(sharedPreferences.edit()) {
            putString("invitationHint", null)
            putString("nickHint", null)
            putString("profilePicturePath", null)
            putInt("theme_num", 1)
            apply()
        }
        binding.invitationInput.text = null
        binding.nickInput.text = null
        recreate(requireActivity())
    }
}