package com.example.a5_marcin_fedorowicz.Settings

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.a5_marcin_fedorowicz.R
import com.example.a5_marcin_fedorowicz.databinding.FragmentPhotoSwipeBinding
import com.example.a5_marcin_fedorowicz.imgDB.ImageRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PhotoSwipeFragment() : Fragment() {

    private lateinit var binding: FragmentPhotoSwipeBinding
    private var photoPos: Int = 0
    private lateinit var images: ArrayList<Uri>
    private lateinit var repo: ImageRepository
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            photoPos = it.getInt("photoId", 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoSwipeBinding.inflate(inflater, container, false)
        repo = ImageRepository.getInstance(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        val adapter = MyPhotoSwipeAdapter(this, images)
        val pager = binding.swiper
        pager.adapter = adapter
        pager.setCurrentItem(photoPos, false)

        binding.setButton.setOnClickListener{
            sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.apply{
                putString("profilePicturePath", images[pager.currentItem].toString())
                apply()
            }
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun loadData(){
        val imagesList = repo.getData()
        images = ArrayList()
        if (imagesList != null) {
            for(image in imagesList){
                val uri: Uri = Uri.parse(image.path)
                uri.let {images.add(it)}
            }
        }
    }
}