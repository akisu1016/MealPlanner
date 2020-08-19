//package com.SquareName.mealplanner.ui.Bookmarklist
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.SquareName.mealplanner.GetRecipe.createService
//import com.SquareName.mealplanner.WebViewActivity
//import android.content.Intent
//import com.SquareName.mealplanner.R
//import com.SquareName.mealplanner.Realms.Task
//import io.realm.Realm
//import io.realm.RealmResults
//import io.realm.Sort
//import java.util.*
//
//class BkmRecyclerViewFragment : Fragment(){
//    private val recipeInterface by lazy { createService() }
//
//    //RecycleView格納変数
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var viewAdapter: RecyclerView.Adapter<*>
//    private lateinit var viewManager: RecyclerView.LayoutManager
//
//    private val realm: Realm by lazy {
//        Realm.getDefaultInstance()
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
////        val root = inflater.inflate(R.layout.fragment_recyclerview, container, false)
////
////        recipeInterface.items().enqueue(object : Callback<List<Item>> {
////            override fun onFailure(call: Call<List<Item>>?, t: Throwable?) {
////                // Log表示(通信失敗)
////                Log.d("fetchItems", "response fail")
////                Log.d("fetchItems", "throwable :$t")
////            }
////
////            override fun onResponse(call: Call<List<Item>>?, response: Response<List<Item>>) {
////                if (response.isSuccessful) {
////                    response.body()?.let {
////                        // Log表示(成功)
////                        Log.d("fetchItems", "response success")
////                        //ここにRicycleviewの処理
////                        viewAdapter =
////                            BkmRecyclerAdapter(it, object : BkmRecyclerAdapter.OnItemClickListener {
////                                override fun onItemClick(
////                                    view: View,
////                                    position: Int,
////                                    clickedText: String
////                                ) {
////                                    ItemClick(view, position, clickedText)
////                                    deleteAll()
////                                    create("TITLE", clickedText)
////                                    Log.d("DB InputCheck",realm.where(Task::class.java).findAll().toString())
////                                }
////                            })
////                        viewManager = LinearLayoutManager(context)
////
////                        with(root) {
////                            recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
////                                // 1.adapterにセット
////                                adapter = viewAdapter
////                                // 2.LayoutMangerをセット
////                                layoutManager = viewManager
////                            }
////                        }
////                    }
////                }
////                // Log表示(ResponseBodyがない)
////                Log.d("fetchItems", "response code:" + response.code())
////                Log.d("fetchItems", "response errorBody:" + response.errorBody())
////            }
////        })
//////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        val root = inflater.inflate(R.layout.fragment_recyclerview, container, false)
//        val value = resources.getStringArray(R.array.URL)
//        val realmList = readAll()
//
//        viewAdapter = BkmRecyclerAdapter(this, realmList, object : BkmRecyclerAdapter.OnItemClickListener{
//            override fun onItemClick(item: Task){//view: View, position: Int, clickedText: String) {
//                ItemClick(item)
////                deleteAll()
////                create("TITLE", clickedText)
////                Log.d("DB InputCheck",realm.where(Task::class.java).findAll().toString())
//            }
//        })
//        viewManager = LinearLayoutManager(context)
//
//        with(root) {
//            recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
//                // 1.adapterにセット
//                adapter = viewAdapter
//                // 2.LayoutMangerをセット
//                layoutManager = viewManager
//            }
//        }
//
//        return root
//    }
//
////    private fun fetchRecipes() {
////        recipeInterface.items().enqueue(object : Callback<List<Item>> {
////            override fun onFailure(call: Call<List<Item>>?, t: Throwable?) {
////                // Log表示(通信失敗)
////                Log.d("fetchItems", "response fail")
////                Log.d("fetchItems", "throwable :$t")
////            }
////
////            override fun onResponse(call: Call<List<Item>>?, response: Response<List<Item>>) {
////                if (response.isSuccessful) {
////                    response.body()?.let {
////                        // Log表示(成功)
////                        Log.d("fetchItems", "response success")
////
////                        //ここに処理
////
////                    }
////                }
////                // Log表示(ResponseBodyがない)
////                Log.d("fetchItems", "response code:" + response.code())
////                Log.d("fetchItems", "response errorBody:" + response.errorBody())
////            }
////        })
////    }
//
//    //リストをクリックしたときの処理
//    fun ItemClick(item: Task){//view: View, position: Int, clickedText: String) {
////        val url = view.itemTextView.text
////        val url = clickedText
//        val url = item.url
//        val intent = Intent(activity, WebViewActivity::class.java)
//        intent.putExtra("url", url)
//        this.startActivity(intent)
//    }
//
//    fun create(title:String = "", url:String = ""){
//        realm.executeTransaction{
//            var task = realm.createObject(Task::class.java, UUID.randomUUID().toString())
//            task.title = title
//            task.url = url
//        }
//    }
//
//    fun readAll(): RealmResults<Task> {
//        return realm.where(Task::class.java).findAll().sort("createdAt", Sort.ASCENDING)
//    }
//
////    fun create(imageId:String = "", recipeName:String = "", recipeUrl:String="", meal:String=""){
////        realm.executeTransaction {
////            var task = realm.createObject(Task::class.java , UUID.randomUUID().toString())
////            task.imageId = imageId
////            task.recipeName = recipeName
////            task.recipeUrl = recipeUrl
////            task.meal = meal
////        }
////    }
//
//    fun delete(id: String) {
//        realm.executeTransaction {
//            val task = realm.where(Task::class.java).equalTo("id", id).findFirst()
//                ?: return@executeTransaction
//            task.deleteFromRealm()
//        }
//    }
//
//    fun deleteAll() {
//        realm.executeTransaction {
//            realm.deleteAll()
//        }
//    }
//}