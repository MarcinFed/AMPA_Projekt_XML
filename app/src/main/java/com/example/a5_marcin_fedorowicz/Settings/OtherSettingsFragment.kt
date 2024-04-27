package com.example.a5_marcin_fedorowicz.Settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.recreate
import com.example.a5_marcin_fedorowicz.R
import com.example.a5_marcin_fedorowicz.databinding.FragmentOtherSettingsBinding

class OtherSettingsFragment : Fragment() {

    private lateinit var binding: FragmentOtherSettingsBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        applyTheme()
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtherSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.theme1.setOnClickListener {
            setPrefs(1)
            true
        }
        binding.theme2.setOnClickListener {
            setPrefs(2)
            true
        }
        binding.theme3.setOnClickListener {
            setPrefs(3)
            true
        }

    }

    companion object {
        fun newInstance() =
            OtherSettingsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun setPrefs(themeNum: Int) {
        val data : SharedPreferences = sharedPreferences
        val editor = data.edit()
        editor.putInt("theme_num", themeNum)
        editor.apply()
        recreate(requireActivity())
    }

    private fun applyTheme() {
        val data : SharedPreferences = sharedPreferences
        val themeNum = data.getInt("theme_num", 0)
        when(themeNum) {
            1 -> {
                context?.theme?.applyStyle(R.style.AppTheme3, true)
            }
            2 -> {
                context?.theme?.applyStyle(R.style.AppTheme1, true)
            }
            3 -> {
                context?.theme?.applyStyle(R.style.AppTheme2, true)
            }
            else -> {
                context?.theme?.applyStyle(R.style.AppTheme3, true)
            }
        }
    }
}