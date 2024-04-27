package com.example.a5_marcin_fedorowicz.Settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.a5_marcin_fedorowicz.R
import com.example.a5_marcin_fedorowicz.databinding.FragmentPhotoBinding

class PhotoFragment : Fragment() {

    private lateinit var binding: FragmentPhotoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        val imgResource = arguments?.getString("imgUri") ?: R.drawable.profile_picture
        Glide.with(binding.photo.context).load(imgResource).into(binding.photo)
        return binding.root
    }
}