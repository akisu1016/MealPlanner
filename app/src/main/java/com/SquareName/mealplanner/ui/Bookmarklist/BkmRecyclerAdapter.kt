package com.SquareName.mealplanner.ui.Bookmarklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.SquareName.mealplanner.GetRecipe.Item
import com.SquareName.mealplanner.R
import com.SquareName.mealplanner.ui.Recyclerview.RecyclerViewHolder

class BkmRecyclerAdapter(
    private val customList: List<Item>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.list_item, parent, false)
        return RecyclerViewHolder(item)
    }

    // recyclerViewのコンテンツのサイズ
    override fun getItemCount(): Int {
        return customList.size
    }

    // ViewHolderに表示する画像とテキストを挿入
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.itemImageView.setImageResource(R.mipmap.ic_launcher_round)
        holder.itemTextView.text = customList[position].title
        holder.itemView.setOnClickListener {
            listener.onItemClick(it, position, customList[position].url)
        }
    }

    //インターフェースの作成
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, clickedText: String)
    }
}