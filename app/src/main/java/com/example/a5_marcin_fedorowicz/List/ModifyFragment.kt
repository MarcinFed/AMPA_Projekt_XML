package com.example.a5_marcin_fedorowicz.List

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.a5_marcin_fedorowicz.DataBase.MyAdapter
import com.example.a5_marcin_fedorowicz.DataBase.MyRepository
import com.example.a5_marcin_fedorowicz.DataBase.MyViewModel
import com.example.a5_marcin_fedorowicz.R
import com.example.a5_marcin_fedorowicz.databinding.FragmentModifyBinding

class ModifyFragment : Fragment() {

    private lateinit var binding: FragmentModifyBinding
    //private var dataRepo : MyRepository? = null
    private val myVModel: MyViewModel by activityViewModels { MyViewModel.Factory }
    lateinit var adapter : MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switchAvailable = binding.availableInput
        switchAvailable.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switchAvailable.thumbTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.switch_thumb_color)
                )
                switchAvailable.trackTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.switch_thumb_color)
                )
            } else {
                switchAvailable.thumbTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.switch_track_color)
                )
                switchAvailable.trackTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.switch_track_color)
                )
            }
        }

        val spinner: Spinner = binding.productTypeInput

        val categories = listOf(
            Category("Other", R.drawable.icons8_other_100),
            Category("Grain", R.drawable.icons8_grain_96),
            Category("Vegetables", R.drawable.icons8_vegetables_96),
            Category("Fruits", R.drawable.icons8_fruits_64),
            Category("Dairy", R.drawable.icons8_milk_96),
            Category("Meat", R.drawable.icons8_meat_100),
            Category("Candy", R.drawable.icons8_candy_96)
        )

        val adapter = CustomSpinnerAdapter(requireContext(), categories)

        spinner.adapter = adapter

        val numberPicker = binding.amountInput
        numberPicker.minValue = 0
        numberPicker.maxValue = 100
        numberPicker.wrapSelectorWheel = false
        numberPicker.value = 0


        val productName = arguments?.getString("productName")
        val use = arguments?.getString("use", "")
        val amount = arguments?.getInt("amount", 0)
        val productType = arguments?.getInt("productType", 0)
        val available = arguments?.getBoolean("available", false)
        val id = arguments?.getInt("id", 0)
        val bought = arguments?.getBoolean("bought", false)

        binding.productInput.setText(productName)
        binding.useInput.setText(use)
        binding.amountInput.value = amount ?: 0
        binding.productTypeInput.setSelection(productType ?: 0)
        binding.availableInput.isChecked = available ?: false
        binding.checkBox.isChecked = bought ?: false

        binding.saveButton.setOnClickListener {
            val productName = binding.productInput.text.toString()
            val use = binding.useInput.text.toString()
            val amount = binding.amountInput.value
            val productType = binding.productTypeInput.selectedItemPosition
            val available = binding.availableInput.isChecked
            val bought = binding.checkBox.isChecked

            val returnBundle = Bundle().apply {
                putString("productName", productName)
                putString("use", use)
                putInt("amount", amount)
                putInt("productType", productType)
                putBoolean("available", available)
                putBoolean("bought", bought)
                putInt("id", id!!)
            }
            myVModel.modifyItem(id!!, productName, use, amount, productType, available, bought)
            //MyRepository.getInstance(requireContext()).modifyItem(id!!, productName, use, amount, productType, available, bought)
            requireActivity().supportFragmentManager.setFragmentResult("item_modified", returnBundle)
            requireActivity().onBackPressed()
        }

        binding.cancelButton.setOnClickListener {
            requireActivity().supportFragmentManager.setFragmentResult("canceled", Bundle.EMPTY)
            requireActivity().onBackPressed()
        }
    }

    companion object {
        fun newInstance() =
            ModifyFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}