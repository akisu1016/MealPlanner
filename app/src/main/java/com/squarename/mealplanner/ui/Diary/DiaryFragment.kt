package com.squarename.mealplanner.ui.Diary

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squarename.mealplanner.GetRecipe.Item
import com.squarename.mealplanner.R
import com.squarename.mealplanner.WebViewActivity
import com.squarename.mealplanner.ui.Recyclerview.RecyclerAdapter
import kotlinx.android.synthetic.main.list_item.view.*
import java.lang.Exception
import java.util.*


class DiaryFragment : Fragment(){

    // Y軸最低スワイプ距離
    private val SWIPE_MIN_DISTANCE = 50
    // Y軸最低スワイプスピード
    private val SWIPE_THRESHOLD_VELOCITY = 200
    // X軸の移動距離 これ以上なら縦移動を判定しない
    private val SWIPE_MAX_OFF_PATH = 200
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
    var week = calendar[Calendar.DAY_OF_WEEK] - 1
    var weektext: Array<TextView?> = arrayOfNulls(7)

    private lateinit var day_label: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_diary, container, false)
        day_label = root.findViewById(R.id.day_label)
        weektext[0] = root.findViewById(R.id.Sunday_day)
        weektext[1] = root.findViewById(R.id.Monday_day)
        weektext[2] = root.findViewById(R.id.Tuesday_day)
        weektext[3] = root.findViewById(R.id.Wednesday_day)
        weektext[4] = root.findViewById(R.id.Thursday_day)
        weektext[5] = root.findViewById(R.id.Friday_day)
        weektext[6] = root.findViewById(R.id.Saturday_day)

        DateSet()

        val items:List<Item> = listOf(
            Item("1", "title", "hogehoge"),
            Item("2", "title", "hugahuga")
        )

        viewAdapter = RecyclerAdapter(items, object : RecyclerAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int, clickedText: String) {
                ItemClick(view, position)
            }
        })
        viewManager = LinearLayoutManager(context)

        with(root) {
            recyclerView = findViewById<RecyclerView>(R.id.day_recycler_view).apply {
                // 1.adapterにセット
                adapter = viewAdapter
                // 2.LayoutMangerをセット
                layoutManager = viewManager
            }
        }

        val gesture = GestureDetector(
            this.activity,
            object : SimpleOnGestureListener() {
                override fun onDown(e: MotionEvent?): Boolean {
                    return true
                }

                override fun onFling(
                    e1: MotionEvent,
                    e2: MotionEvent,
                    velocityX: Float,
                    velocityY: Float
                ): Boolean {
                    var result = false
                    try {
                        val diffY = e2.getY() - e1.getY();
                        val diffX = e2.getX() - e1.getX();
                        if (Math.abs(diffX) > Math.abs(diffY)) {
                            if (Math.abs(diffX) > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                if (diffX > 0) {
                                    BackDay()
                                } else {
                                    AddDay()
                                }
                                result = true
                            }
                        }
                    }catch (e: Exception) {
                        e.printStackTrace();
                    }
                    return result

//                    try {
//                        // 移動距離・スピードを出力
//                        val distance_y = Math.abs(e1.y - e2.y)
//                        val velocity_y = Math.abs(velocityY)
//                        Log.d("onFling", "縦の移動距離:$distance_y 縦の移動スピード:$velocity_y")
//
//                        // X軸の移動距離が大きすぎる場合
//                        if (Math.abs(e1.x - e2.x) > SWIPE_MAX_OFF_PATH) {
//                            Log.d("onFling", "横の移動距離が大きすぎます")
//
//                            // 開始位置から終了位置の移動距離が指定値より大きい
//                            // Y軸の移動速度が指定値より大きい
//                        } else if (e2.y - e1.y > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
//                            Log.d("onFling", "上から下")
//
//                            // 終了位置から開始位置の移動距離が指定値より大きい
//                            // Y軸の移動速度が指定値より大きい
//                        } else if (e1.y - e2.y > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
//                            Log.d("onFling", "下から上")
//                        }
//
//                    } catch (e: Exception) {
//                        // TODO
//                    }
//                    return super.onFling(e1, e2, velocityX, velocityY)

                }
            })

        root.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return gesture.onTouchEvent(event)
            }
        })

        return root
    }

    //リストをクリックしたときの処理
    fun ItemClick(view: View, position: Int) {
        val url = view.itemTextView.text
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra("url", url)
        this.startActivity(intent)
    }

    //日付データのセット
    fun DateSet(){
        day_label.text = "${year}年${month}月${day}日 ${weekdays[week]}曜日"
        weektext[week]?.setBackgroundColor(Color.WHITE)
        weektext[week]?.setTextColor(Color.BLACK)

        var calcnum = 0
        for(i in week downTo 0){
            weektext[i]?.text = calendar[Calendar.DATE].toString()
            calendar = Calendar.getInstance()
            calcnum--
            calendar.add(Calendar.DATE, calcnum)
        }
        calcnum = 0
        for(i in (week+1)..6){
            calendar = Calendar.getInstance()
            calcnum++
            calendar.add(Calendar.DATE, calcnum)
            weektext[i]?.text = calendar[Calendar.DATE].toString()
        }
        calendar = Calendar.getInstance()
    }

    fun BackDay(){
        weektext[week]?.setBackgroundResource(R.color.md_deep_orange_500)
        weektext[week]?.setTextColor(Color.WHITE)
        calendar.add(Calendar.DATE, -1)
        year = calendar[Calendar.YEAR]
        month = calendar[Calendar.MONTH] + 1
        day = calendar[Calendar.DATE]
        if(week == 0){
            week = 6
        }else{
            week--
        }
        weektext[week]?.setBackgroundColor(Color.WHITE)
        weektext[week]?.setTextColor(Color.BLACK)
        day_label.text = "${year}年${month}月${day}日 ${weekdays[week]}曜日"
    }

    fun AddDay(){
        weektext[week]?.setBackgroundResource(R.color.md_deep_orange_500)
        weektext[week]?.setTextColor(Color.WHITE)
        calendar.add(Calendar.DATE, 1)
        year = calendar[Calendar.YEAR]
        month = calendar[Calendar.MONTH] + 1
        day = calendar[Calendar.DATE]
        if(week == 6){
            week = 0
        }else{
            week++
        }
        weektext[week]?.setBackgroundColor(Color.WHITE)
        weektext[week]?.setTextColor(Color.BLACK)
        day_label.text = "${year}年${month}月${day}日 ${weekdays[week]}曜日"

    }

    fun SetDay(){

    }


}