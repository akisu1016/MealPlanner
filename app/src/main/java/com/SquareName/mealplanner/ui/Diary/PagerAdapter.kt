package com.SquareName.mealplanner.ui.Diary

import android.view.PointerIcon
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter


class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun createFragment(position: Int): Fragment {
        return CalendarFragment()
    }

}


//class FragmentStatePagerAdapter(fm: FragmentManager) :
//    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
//
//    // 表示するフラグメントを制御する
//    override fun getItem(position: Int): Fragment {
//
//    }
//
//    override fun isViewFromObject(view: View, `object`: Any): Boolean {
//        return view == `object`
//    }
//
//    // viewPagerにセットするコンテンツ(フラグメントリスト)のサイズ
//    override fun getCount(): Int {
//        return 3
//    }
//
//    override fun getPageTitle(position: Int): CharSequence? {
//        return "Page $position"
//    }
//}