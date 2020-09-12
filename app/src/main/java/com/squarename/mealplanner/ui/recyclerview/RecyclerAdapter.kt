package com.squarename.mealplanner.ui.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.squarename.mealplanner.getrecipe.Item
import com.squarename.mealplanner.R
import com.squarename.mealplanner.getrecipe.Recipe


class RecyclerAdapter(
    private val customList: List<Recipe>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerViewHolder>() {

    // getItemCount onCreateViewHolder onBindViewHolderを実装
    // 上記のViewHolderクラスを使ってViewHolderを作成
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
        // リンク画像を表示する
        val url = customList[position].imgUrl
        holder.itemImageView.load(url)
        // テキスト
        holder.itemTextView.text = customList[position].title
        // Clickイベント
        holder.itemView.setOnClickListener {
            listener.onItemClick(it, position, customList[position].url)
        }
        holder.itemView.setOnLongClickListener {
            listener.onItemLongClick(it, position, customList[position].url)
            true
        }
    }

    //インターフェースの作成
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, clickedText: String)
        fun onItemLongClick(view: View, position: Int, clickedText: String)
    }
}
