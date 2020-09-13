package com.squarename.mealplanner.ui.bookmarklist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squarename.mealplanner.R
import com.squarename.mealplanner.WebViewActivity
import com.squarename.mealplanner.getrecipe.Recipe
import com.squarename.mealplanner.rmethods.RealmMethod
import com.squarename.mealplanner.ui.recyclerview.RecyclerAdapter
import android.app.AlertDialog
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_recyclerview.view.*

class BkmListFragment: Fragment() {
    var items = listOf<Recipe>()
    val realm = RealmMethod()

    //RecycleView格納変数
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        items = realm.readBkm()

        val root = inflater.inflate(R.layout.fragment_recyclerview, container, false)

        if(items.isNotEmpty()){
            viewAdapter = RecyclerAdapter(items, object : RecyclerAdapter.OnItemClickListener{
                override fun onItemClick(view: View, position: Int, clickedText: String) {
                    val imgUrl = items[position].imgUrl
                    ItemClick(view, position, clickedText, imgUrl)// webviewでのimgUrlの取得方法がわからん過ぎるので無理やり取っておく
                }

                override fun onItemLongClick(view: View, position: Int, clickedText: String) {
                    val title = items[position].title
                    val imgUrl = items[position].imgUrl
                    ItemLongClick(clickedText,this@BkmListFragment)
                }
            })
            viewManager = LinearLayoutManager(context)

            with(root) {
                recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
                    // 1.adapterにセット
                    adapter = viewAdapter
                    // 2.LayoutMangerをセット
                    layoutManager = viewManager
                }
            }
        }else{
            root.ExistText.text = "ブックマークに登録されていません"
        }

        return root
    }

    override fun onResume() {
        super.onResume()
    }

    //リストをクリックしたときの処理
    fun ItemClick(view: View, position: Int, clickedText: String, imgUrl: String) {
        val url = clickedText
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra("url", url)
        intent.putExtra("imgUrl", imgUrl)
        this.startActivity(intent)
    }
    fun ItemLongClick(clickedText: String, act: Fragment){
        RealmMethod().bkmDelDialog(clickedText,act)
    }
}