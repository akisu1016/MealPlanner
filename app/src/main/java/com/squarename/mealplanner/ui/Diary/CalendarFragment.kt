package com.squarename.mealplanner.ui.Diary

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squarename.mealplanner.GetRecipe.Item
import com.squarename.mealplanner.R
import com.squarename.mealplanner.WebViewActivity
import com.squarename.mealplanner.databinding.FragmenrtCalendarRecyclerviewBinding
import com.squarename.mealplanner.ui.Recyclerview.RecyclerAdapter
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.*


class CalendarFragment : Fragment() {

    private lateinit var binding: FragmenrtCalendarRecyclerviewBinding

    //RecycleView格納変数
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    //日付格納変数
    val weekdays: Array<String> = arrayOf("日", "月", "火", "水", "木", "金", "土")
    var calendar = Calendar.getInstance()
    var year = calendar[Calendar.YEAR]
    var month = calendar[Calendar.MONTH] + 1
    var day = calendar[Calendar.DATE]
    val minDay = 1
    var week = calendar[Calendar.DAY_OF_WEEK] - 1
    val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val blankDays = calendar.get(Calendar.DAY_OF_WEEK)
    val dayArray: MutableList<String> = mutableListOf()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val root = inflater.inflate(R.layout.fragmenrt_calendar_recyclerview, container, false)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragmenrt_calendar_recyclerview, container, false)
        binding.data = DiaryData()


//        tems = listOf(
//            Item("1", "title", "hogehoge"),
//            Item("2", "title", "hugahuga")
//        )



//        if(items != null){
//            viewAdapter = RecyclerAdapter(items!!, object : RecyclerAdapter.OnItemClickListener{
//                override fun onItemClick(view: View, position: Int, clickedText: String) {
//                    ItemClick(view, position)
//                }
//            })
//            viewManager = LinearLayoutManager(context)
//
//            with(binding.root) {
//                recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
//                    // 1.adapterにセット
//                    adapter = viewAdapter
//                    // 2.LayoutMangerをセット
//                    layoutManager = viewManager
//                }
//            }
//        }

        return binding.root
    }

    //リストをクリックしたときの処理
    fun ItemClick(view: View, position: Int) {
        val url = view.itemTextView.text
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra("url", url)
        this.startActivity(intent)
    }
}