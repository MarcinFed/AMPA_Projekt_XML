package com.example.a5_marcin_fedorowicz.List

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.a5_marcin_fedorowicz.R
import com.example.a5_marcin_fedorowicz.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var sharedPreferences: SharedPreferences
    var productName: String? = null
    var use: String? = null
    var amount: Int? = null
    var productType: Int? = null
    var available: Boolean? = null
    var bought: Boolean? = null
    var id: Int? = null
    var detailsSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("product_data", Context.MODE_PRIVATE)

        requireActivity().supportFragmentManager.setFragmentResultListener("item_modified", this) { requestKey, bundle ->
            saveDataToSharedPreferences(bundle)
            loadDataFromSharedPreferences()
            setDetails()
        }

        requireActivity().supportFragmentManager.setFragmentResultListener("canceled", this) { _, _ ->
            loadDataFromSharedPreferences()
            setDetails()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productName = arguments?.getString("productName")
        use = arguments?.getString("use", "Not specified")
        amount = arguments?.getInt("amount", 0)
        productType = arguments?.getInt("productType", 0)
        available = arguments?.getBoolean("available", false)
        bought = arguments?.getBoolean("bought", false)
        id = arguments?.getInt("id", 0)

        setDetails()

        binding.modifyButton.setOnClickListener {
            val modifyFragment = ModifyFragment.newInstance()

            val bundle = Bundle().apply {
                if(productName != "Not specified")
                    putString("productName", productName)
                putString("use", use)
                putInt("amount", amount!!)
                putInt("productType", productType!!)
                putBoolean("available", available!!)
                putBoolean("bought", bought!!)
                putInt("id", id!!)
            }

            modifyFragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.dfContainer, modifyFragment)
                .commit()
        }

        binding.backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    companion object {
        fun newInstance() =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun setDetails() {
        val boughtText = if (bought == true) {
            "Bought"
        } else {
            "Not bought yet"
        }

        productName = if (productName == "") {
            "Not specified"
        } else {
            productName
        }

        val availabilityText = if (available == true) {
            "was"
        } else {
            "wasn't"
        }

        var productCategory: String = ""
        when(productType) {
            0 -> productCategory = "Other"
            1 -> productCategory = "Grain"
            2 -> productCategory = "Vegetables"
            3 -> productCategory = "Fruits"
            4 -> productCategory = "Dairy"
            5 -> productCategory = "Meat"
            6 -> productCategory = "Candy"
        }

        binding.product.text = productName
        binding.use.text = getString(R.string.product_use) + " " + use
        binding.amount.text = getString(R.string.amount_detail) + " " + amount
        binding.productType.text = productCategory
        binding.available.text = getString(R.string.available_1) + " " + availabilityText + " " + getString(
            R.string.available_2
        )
        binding.bought.text = boughtText

        when (productType) {
            0 -> binding.category.setImageResource(R.drawable.icons8_other_100)
            1 -> binding.category.setImageResource(R.drawable.icons8_grain_96)
            2 -> binding.category.setImageResource(R.drawable.icons8_vegetables_96)
            3 -> binding.category.setImageResource(R.drawable.icons8_fruits_64)
            4 -> binding.category.setImageResource(R.drawable.icons8_milk_96)
            5 -> binding.category.setImageResource(R.drawable.icons8_meat_100)
            6 -> binding.category.setImageResource(R.drawable.icons8_candy_96)
        }
    }

    private fun saveDataToSharedPreferences(bundle: Bundle) {
        with(sharedPreferences.edit()) {
            putString("productName", bundle.getString("productName", ""))
            putString("use", bundle.getString("use", ""))
            putInt("amount", bundle.getInt("amount", 0))
            putInt("productType", bundle.getInt("productType", 0))
            putBoolean("available", bundle.getBoolean("available", false))
            putBoolean("bought", bundle.getBoolean("bought", false))
            putInt("id", bundle.getInt("id", 0))
            apply()
        }
    }

    private fun loadDataFromSharedPreferences() {
        productName = sharedPreferences.getString("productName", "")
        use = sharedPreferences.getString("use", "Not specified")
        amount = sharedPreferences.getInt("amount", 0)
        productType = sharedPreferences.getInt("productType", 0)
        available = sharedPreferences.getBoolean("available", false)
        bought = sharedPreferences.getBoolean("bought", false)
        id = sharedPreferences.getInt("id", 0)
    }
}