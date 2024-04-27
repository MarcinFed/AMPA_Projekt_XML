package com.example.a5_marcin_fedorowicz.Settings

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.text.FieldPosition

class MyPhotoSwipeAdapter(fragment: Fragment, private val data: List<Uri>): FragmentStateAdapter(fragment){

    override fun getItemCount(): Int {
        return data.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = PhotoFragment()
        val bundle = Bundle().apply {
            putString("imgUri", data[position].toString())
        }
        fragment.arguments = bundle
        return fragment
    }
}