package com.SquareName.mealplanner.ui.Recyclerview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.SquareName.mealplanner.R
import com.SquareName.mealplanner.WebViewActivity
import kotlinx.android.synthetic.main.list_item.view.*


class RecycleviewFragment : Fragment(){

    //RecycleView格納変数
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_recyclerview, container, false)
        val value = resources.getStringArray(R.array.URL)

        viewAdapter = RecyclerAdapter(value, object : RecyclerAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int, clickedText: String) {
                ItemClick(view, position)
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
    fun ItemClick(view: View, position: Int) {
        val url = view.itemTextView.text
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra("url", url)
        this.startActivity(intent)
    }

}