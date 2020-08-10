package com.SquareName.mealplanner.ui.Diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.SquareName.mealplanner.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragmenrt_calendar_recyclerview.view.*
import kotlinx.android.synthetic.main.fragment_diary_viewpager.view.*

class DiaryViewPagerFragment : Fragment() {

    val weekdays: Array<String> = arrayOf("日", "月", "火", "水", "木", "金", "土")

    private lateinit var fragmentstateadapter: FragmentStateAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root = inflater.inflate(R.layout.fragment_diary_viewpager, container, false)
        val tab = inflater.inflate(R.layout.fragmenrt_calendar_recyclerview,container, false)

        viewPager = root.findViewById(R.id.pager)
        fragmentstateadapter = PagerAdapter(this)
        viewPager.adapter = fragmentstateadapter
        viewPager.setCurrentItem(Int.MAX_VALUE / 2, true)


//        val tabLayout = tab.findViewById<TabLayout>(R.id.tab_layout)
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            tab.text = "${weekdays[position]}"
//        }.attach()

        return root
    }
    

}

