package com.squarename.mealplanner.ui.diary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squarename.mealplanner.R
import com.squarename.mealplanner.WebViewActivity
import com.squarename.mealplanner.databinding.FragmenrtCalendarRecyclerviewBinding
import com.squarename.mealplanner.getrecipe.Item
import com.squarename.mealplanner.rmethods.RealmMethod
import com.squarename.mealplanner.ui.recyclerview.RecyclerAdapter
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment(position: Int) : Fragment() {

    private lateinit var binding: FragmenrtCalendarRecyclerviewBinding
    val position = position
    var items = listOf<Item>()
    val realm = RealmMethod()

    //RecycleView格納変数
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    //日付格納変数
    val default = Int.MAX_VALUE / 2
    val weekdays: Array<String> = arrayOf("日", "月", "火", "水", "木", "金", "土")
    var calendar = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmenrt_calendar_recyclerview, container, false)
        calendar.add(Calendar.DATE, position - default)
        var year = calendar[Calendar.YEAR]
        var month = calendar[Calendar.MONTH] + 1
        var day = calendar[Calendar.DATE]
        var week = calendar[Calendar.DAY_OF_WEEK] - 1
        binding.daylabel.text = "${year}年${month}月${day}日 ${weekdays[week]}曜日"


        //日付をフォーマットしてRealmから検索
        try {
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            val date: String = sdf.format(calendar.time)
            items = realm.readFromTime(date)
        } catch (e: ParseException) {
            Log.d("e", e.toString())
        }


        //リストが存在した場合表示
        if(items.equals("")){
            viewAdapter = RecyclerAdapter(items, object : RecyclerAdapter.OnItemClickListener{
                override fun onItemClick(view: View, position: Int, clickedText: String) {
                    ItemClick(view)
                }
            })
            viewManager = LinearLayoutManager(context)

            with(binding.root) {
                recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
                    // 1.adapterにセット
                    adapter = viewAdapter
                    // 2.LayoutMangerをセット
                    layoutManager = viewManager
                }
            }
        }else{
            binding.ExistText.text = "この日にレシピは登録されていません"
        }

        return binding.root
    }

    //リストをクリックしたときの処理
    fun ItemClick(view: View) {
        val url = view.itemTextView.text
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra("url", url)
        this.startActivity(intent)
    }
}