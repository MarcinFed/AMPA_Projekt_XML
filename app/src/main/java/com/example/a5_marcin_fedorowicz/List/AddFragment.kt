package com.example.a5_marcin_fedorowicz.List

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.a5_marcin_fedorowicz.DataBase.DBItem
import com.example.a5_marcin_fedorowicz.DataBase.MyRepository
import com.example.a5_marcin_fedorowicz.DataBase.MyViewModel
import com.example.a5_marcin_fedorowicz.Repo.DataItem
import com.example.a5_marcin_fedorowicz.Repo.DataRepo
import com.example.a5_marcin_fedorowicz.R
import com.example.a5_marcin_fedorowicz.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val myVModel: MyViewModel by activityViewModels { MyViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val saveButton = binding.saveButton
        saveButton.setOnClickListener{
            saveProduct()
        }
        val switchAvailable = binding.availableInput
        switchAvailable.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switchAvailable.thumbTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.white)
                )
                switchAvailable.trackTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.white)
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

        val cancelButton = binding.cancelButton
        cancelButton.setOnClickListener {
            cancelAdding()
        }

    }

    private fun saveProduct() {
        val productName = binding.productInput.text.toString()
        val use = binding.useInput.text.toString()
        val amount = binding.amountInput.value
        val productType = binding.productTypeInput.selectedItemPosition
        val available = binding.availableInput.isChecked

        val newProduct = DBItem(productName, use, amount, productType, available)

//        if(MyRepository.getInstance(requireContext()).addItem(newProduct))
//            parentFragmentManager.setFragmentResult("item_added", Bundle.EMPTY)
        myVModel.insertItem(newProduct)

        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun cancelAdding() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    companion object {
        fun newInstance() =
            AddFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}