//package com.SquareName.mealplanner.ui.Bookmarklist
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.SquareName.mealplanner.R
//import com.SquareName.mealplanner.Realms.Task
//import com.SquareName.mealplanner.ui.Recyclerview.RecyclerViewHolder
//import io.realm.OrderedRealmCollection
//
//class BkmRecyclerAdapter(
//    private val context: BkmRecyclerViewFragment,
//    private val customList: OrderedRealmCollection<Task>?,
//    private val listener: OnItemClickListener,
//    private val autoUpdate: Boolean
//) : RecyclerView.Adapter<RecyclerViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
//        val item = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
////        val layoutInflater = LayoutInflater.from(parent.context)
////        val item = layoutInflater.inflate(R.layout.list_item, parent, false)
//        return RecyclerViewHolder(item)
//    }
//
//    // recyclerViewのコンテンツのサイズ
//    override fun getItemCount(): Int {
//        return customList?.size ?: 0//customList.size
//    }
//
//    // ViewHolderに表示する画像とテキストを挿入
//    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
//        val task: Task = customList?.get(position) ?: return
//        holder.itemImageView.setImageResource(R.mipmap.ic_launcher_round)
//        holder.itemTextView.text =task.url
//        holder.itemView.setOnClickListener {
//            listener.onItemClick(task)//(it, position, task.url)
//        }
//    }
//
//    //インターフェースの作成
//    interface OnItemClickListener {
//        fun onItemClick(item: Task)//(view: View, position: Int, clickedText: String)
//    }
//}