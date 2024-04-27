package com.example.a5_marcin_fedorowicz.List

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.a5_marcin_fedorowicz.R


class CustomSpinnerAdapter(
    context: Context,
    private val categories: List<Category>
) : ArrayAdapter<Category>(context, R.layout.spinner_item_layout, categories) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.spinner_item_layout,
            parent,
            false
        )

        val category = getItem(position)
        if (category != null) {
            val imageViewIcon: ImageView = itemView.findViewById(R.id.imageViewIcon)
            val textViewCategory: TextView = itemView.findViewById(R.id.textViewCategory)

            imageViewIcon.setImageResource(category.iconResId)
            textViewCategory.text = category.name
        }

        return itemView
    }
}