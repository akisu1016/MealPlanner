package com.SquareName.mealplanner.ui.Recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.SquareName.mealplanner.R


class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val itemTextView: TextView = view.findViewById(R.id.itemTextView)
    val itemImageView: ImageView = view.findViewById(R.id.itemImageView)

    init {
        // layoutの初期設定するときはココ
    }

}