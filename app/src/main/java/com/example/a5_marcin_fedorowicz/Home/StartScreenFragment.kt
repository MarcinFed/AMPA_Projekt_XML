package com.example.a5_marcin_fedorowicz.Home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import com.example.a5_marcin_fedorowicz.R
import com.example.a5_marcin_fedorowicz.databinding.FragmentStartScreenBinding

class StartScreenFragment : Fragment() {

    private lateinit var binding: FragmentStartScreenBinding
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
        binding = FragmentStartScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val invitationText = sharedPreferences.getString("invitationHint", getString(R.string.hello))
        val nickText = sharedPreferences.getString("nickHint", getString(R.string.nick))
        val profilePicture = sharedPreferences.getString("profilePicturePath", null)

        binding.invitation.text = invitationText
        binding.nick.text = nickText

        if (!profilePicture.isNullOrEmpty()) {
            binding.imageView.setImageURI(profilePicture.toUri())
        } else {
            binding.imageView.setImageResource(R.drawable.profile_picture)
        }
    }

    companion object {
        private const val REQUEST_PERMISSION_CODE = 123
        fun newInstance() =
            StartScreenFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}