package com.example.a5_marcin_fedorowicz.Settings

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MySettingsAdapter(fg: Fragment): FragmentStateAdapter(fg) {

    companion object {
        const val PAGE_COUNT = 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PhotoSettingsFragment.newInstance()
            1 -> HomeScreenSettingsFragment.newInstance()
            2 -> OtherSettingsFragment.newInstance()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun getItemCount(): Int {
        return PAGE_COUNT
    }
}