package com.example.a5_marcin_fedorowicz.Settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a5_marcin_fedorowicz.databinding.FragmentSettingsBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val MSAdapter = MySettingsAdapter(this)
        val tabLayout = binding.tabLayout
        val vPager = binding.viewPager
        vPager.adapter = MSAdapter

        val tabLM = TabLayoutMediator(tabLayout, vPager, object : TabLayoutMediator.TabConfigurationStrategy
        {
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {

                when (position) {
                    0 -> tab.text = "Photo"
                    1 -> tab.text = "Home screen"
                    2 -> tab.text = "Other"

                }
            }
        })

        tabLM.attach()
    }

    companion object {
        fun newInstance() =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}