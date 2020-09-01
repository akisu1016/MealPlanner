package com.squarename.mealplanner.ui.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.squarename.mealplanner.R
import com.squarename.mealplanner.databinding.FragmentDiaryViewpagerBinding
import com.google.android.material.tabs.TabLayout

class DiaryViewPagerFragment : Fragment() {

    private lateinit var fragmentstateadapter: FragmentStateAdapter
    private lateinit var binding: FragmentDiaryViewpagerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diary_viewpager, container, false)
        fragmentstateadapter = PagerAdapter(this)
        binding.pager.adapter = fragmentstateadapter
        binding.pager.setCurrentItem(Int.MAX_VALUE / 2, true)

        return binding.root
    }
    

}

