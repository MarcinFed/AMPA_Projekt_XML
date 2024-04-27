package com.example.a5_marcin_fedorowicz.List

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a5_marcin_fedorowicz.DataBase.DBItem
import com.example.a5_marcin_fedorowicz.DataBase.MyRepository
import com.example.a5_marcin_fedorowicz.DataBase.MyAdapter
import com.example.a5_marcin_fedorowicz.DataBase.MyViewModel
import com.example.a5_marcin_fedorowicz.R
import com.example.a5_marcin_fedorowicz.databinding.FragmentListBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    //private var dataRepo : MyRepository? = null
    private val myVModel: MyViewModel by activityViewModels { MyViewModel.Factory }
    lateinit var adapter : MyAdapter

    var onItemAction : (item: DBItem, action:Int)->Unit = { item, action ->
        when(action) {
            1 -> {
                val bundle = Bundle().apply {
                    putString("productName", item.Product)
                    putString("use", item.Use)
                    putInt("amount", item.Amount)
                    putInt("productType", item.ProductType)
                    putBoolean("available", item.Available)
                    putBoolean("bought", item.Bought)
                    putInt("id", item.id)
                }
                findNavController().navigate(R.id.action_listFragment_to_detailsFragment, bundle)
            }
            2 -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                builder.setTitle(resources.getString(R.string.yousure))
                builder.setMessage(resources.getString(R.string.sure))
                builder.setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
//                    dataRepo?.deleteItem(item)
//                    adapter.submitList(dataRepo?.getData())
//                    myVModel.deleteItem(item)
                    myVModel.viewModelScope.launch {
                        myVModel.deleteItem(item)
                    }
//                    adapter.submitList(myVModel.getAllItems())
                }
                builder.setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                    dialog.cancel()
                }
                builder.create().show()
            }
            3 -> {
                myVModel.modifyItem(item.id, item.Product, item.Use, item.Amount, item.ProductType, item.Available, item.Bought)
//                dataRepo?.modifyItem(item.id, item.Product, item.Use, item.Amount, item.ProductType, item.Available, item.Bought)
                //notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //dataRepo = MyRepository.getInstance(requireContext())
        adapter = MyAdapter(onItemAction)
//        adapter.submitList(dataRepo!!.getData())

//        parentFragmentManager.setFragmentResultListener("item_added", this) {
//            requestKey, _ ->
//            adapter.data = dataRepo!!.getData()!!
//            adapter.notifyDataSetChanged()
//        }
//
//        parentFragmentManager.setFragmentResultListener("item_modify", this) {
//                requestKey, _ ->
//            adapter.data = dataRepo!!.getData()!!
//            adapter.notifyDataSetChanged()
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fab: FloatingActionButton = binding.floatingActionButton
        fab.setOnClickListener { view ->
            showBottomSheetDialog()
        }

        val recView = binding.myRecView
        recView.layoutManager = LinearLayoutManager(requireContext())
        recView.adapter = adapter
        myVModel.viewModelScope.launch {
            myVModel.getAllItems()?.collect {
                adapter.submitList(it)
            }
        }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet_menu, null)
        bottomSheetDialog.setContentView(view)

        val addButton = view.findViewById<TextView>(R.id.textAdd)
        val addImage = view.findViewById<ImageView>(R.id.add_icon)

        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
            bottomSheetDialog.dismiss()
        }

        addImage.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    companion object {
        fun newInstance() =
            ListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}