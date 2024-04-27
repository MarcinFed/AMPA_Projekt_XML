package com.example.a5_marcin_fedorowicz.DataBase

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a5_marcin_fedorowicz.R
import com.example.a5_marcin_fedorowicz.databinding.ListRowBinding

class MyAdapter(private val onItemAction: (item:DBItem, action:Int)->Unit) : ListAdapter<DBItem, MyAdapter.MyViewHolder>(DiffCallback){
    inner class MyViewHolder(viewBinding: ListRowBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        val tv1: TextView = viewBinding.lrowTv1
        val tv2: TextView = viewBinding.lrowTv2
        val img: ImageView = viewBinding.lrowImage
        val cBox: CheckBox = viewBinding.lrowCheckBox
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewBinding = ListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MyViewHolder(viewBinding)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            onItemAction(getItem(position), 1)
        }
        holder.itemView.setOnLongClickListener {
            val position = holder.adapterPosition
            onItemAction(getItem(position), 2)
            true
        }
        holder.cBox.setOnClickListener { v ->
            val position = holder.adapterPosition
            val item = getItem(position)
            item.Bought = (v as CheckBox).isChecked
            onItemAction(item, 3)
        }

        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currItem = getItem(position)

        holder.tv1.text = currItem.Product
        holder.tv2.text = "Amount: " + currItem.Amount
        holder.cBox.isChecked = currItem.Bought

        when (currItem.ProductType) {
            0 -> holder.img.setImageResource(R.drawable.icons8_other_100)
            1 -> holder.img.setImageResource(R.drawable.icons8_grain_96)
            2 -> holder.img.setImageResource(R.drawable.icons8_vegetables_96)
            3 -> holder.img.setImageResource(R.drawable.icons8_fruits_64)
            4 -> holder.img.setImageResource(R.drawable.icons8_milk_96)
            5 -> holder.img.setImageResource(R.drawable.icons8_meat_100)
            6 -> holder.img.setImageResource(R.drawable.icons8_candy_96)
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DBItem>() {
            override fun areItemsTheSame(oldItem: DBItem, newItem: DBItem): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: DBItem, newItem: DBItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
