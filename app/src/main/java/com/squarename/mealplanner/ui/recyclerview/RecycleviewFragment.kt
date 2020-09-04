package com.squarename.mealplanner.ui.recyclerview

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
import com.squarename.mealplanner.getrecipe.createService
import com.squarename.mealplanner.R
import com.squarename.mealplanner.WebViewActivity
import com.squarename.mealplanner.getrecipe.Recipe
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import com.squarename.mealplanner.rmethods.RealmMethod
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecycleviewFragment : Fragment() {

    private val recipeInterface by lazy { createService() }

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

        val root = inflater.inflate(R.layout.fragment_recyclerview, container, false)

        recipeInterface.recipes(material).enqueue(object : Callback<List<Recipe>> {
            override fun onFailure(call: Call<List<Recipe>>?, t: Throwable?) {
                // Log表示(通信失敗)
                Log.d("fetchRecipe", "response fail")
                Log.d("fetchRecipe", "throwable :$t")
            }

            override fun onResponse(call: Call<List<Recipe>>?, response: Response<List<Recipe>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        // Log表示(成功)
                        Log.d("fetchRecipe", "response success")

                        //ここにRicycleviewの処理
                        viewAdapter =
                            RecyclerAdapter(it, object : RecyclerAdapter.OnItemClickListener {
                                override fun onItemClick(
                                    view: View,
                                    position: Int,
                                    clickedText: String
                                ) {
                                    val imgUrl = it[position].imgUrl
                                    ItemClick(view, position, clickedText, imgUrl)// webviewでのimgUrlの取得方法がわからん過ぎるので無理やり取っておく
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
                    }
                }
                // Log表示(ResponseBodyがない)
                Log.d("fetchRecipe", "response code:" + response.code())
                Log.d("fetchRecipe", "response errorBody:" + response.errorBody())
            }
        })

        return root
    }

    //リストをクリックしたときの処理
    fun ItemClick(view: View, position: Int, clickedText: String , imgUrl: String) {
        val url = clickedText
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra("url", url)
        intent.putExtra("imgUrl", imgUrl)
        this.startActivity(intent)
    }

    // 検索Fragment生成時にActivityから受け取る値（検索ワード）をArgumentに設定する
    companion object {
        fun newInstance(articleId: String?): RecycleviewFragment {
            val args = Bundle()
            args.putString("MATERIAL", articleId)
            val fragment = RecycleviewFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
