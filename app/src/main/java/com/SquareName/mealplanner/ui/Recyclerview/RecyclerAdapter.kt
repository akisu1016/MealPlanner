package com.SquareName.mealplanner.ui.Recyclerview

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.SquareName.mealplanner.R
import kotlinx.android.synthetic.main.list_item.view.*


class RecyclerAdapter(private val customList: Array<String>, private val listener: OnItemClickListener) : RecyclerView.Adapter<RecyclerViewHolder>(){

    // リスナー格納変数
//    private lateinit var listener: OnItemClickListener

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
        holder.itemImageView.setImageResource(R.mipmap.ic_launcher_round)
        holder.itemTextView.text = customList[position]
        holder.itemView.setOnClickListener {
            listener.onItemClick(it, position, customList[position])
        }
    }

    //インターフェースの作成
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, clickedText: String)
    }

    // リスナー
//    fun setOnItemClickListener(listener: OnItemClickListener) {
//        this.listener = listener
//    }

}


//class RecyclerAdapter(private val context: Context, private val itemClickListener: Unit, private val itemList:List<String>)
//    : RecyclerView.Adapter<RecyclerViewHolder>() {
//
//    private var mRecyclerView : RecyclerView? = null
//
//    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
//        super.onAttachedToRecyclerView(recyclerView)
//        mRecyclerView = recyclerView
//    }
//
//    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
//        super.onDetachedFromRecyclerView(recyclerView)
//        mRecyclerView = null
//
//    }
//
//    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
//        holder?.let {
//            it.itemTextView.text = itemList.get(position)
//            it.itemImageView.setImageResource(R.mipmap.ic_launcher)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return itemList.size
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
//
//        val layoutInflater = LayoutInflater.from(context)
//        val mView = layoutInflater.inflate(R.layout.list_item, parent, false)
//
//        mView.setOnClickListener { view ->
//            mRecyclerView?.let {
//                itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
//            }
//        }
//
//        return RecyclerViewHolder(mView)
//    }
//
//}