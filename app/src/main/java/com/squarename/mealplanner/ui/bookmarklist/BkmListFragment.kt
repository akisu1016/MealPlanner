package com.squarename.mealplanner.ui.bookmarklist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squarename.mealplanner.getrecipe.Item
import com.squarename.mealplanner.R
import com.squarename.mealplanner.WebViewActivity
import com.squarename.mealplanner.rmethods.RealmMethod
import com.squarename.mealplanner.ui.recyclerview.RecyclerAdapter

class BkmListFragment: Fragment() {
    var items = listOf<Item>()
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
        // 検索した食材
        val args = arguments
        var material = ""
        if(args != null) {
            material = args?.getString("MATERIAL").toString()
            Log.d("get argment", "response :$material")
        } else {
            Log.d("get argment", "response :false")
        }

        items = realm.readBkm()

        val root = inflater.inflate(R.layout.fragment_recyclerview, container, false)

        viewAdapter = RecyclerAdapter(items, object : RecyclerAdapter.OnItemClickListener{
            override fun onItemClick(
                view: View,
                position: Int,
                clickedText: String
            ) {
                ItemClick(view, position, clickedText)
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

        return root
    }

    //リストをクリックしたときの処理
    fun ItemClick(view: View, position: Int, clickedText: String) {
        val url = clickedText
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra("url", url)
        this.startActivity(intent)
    }
}