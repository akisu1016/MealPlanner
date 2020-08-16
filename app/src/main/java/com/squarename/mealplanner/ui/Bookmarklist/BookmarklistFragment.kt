package com.squarename.mealplanner.ui.Bookmarklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squarename.mealplanner.R

class BookmarklistFragment : Fragment() {

    private lateinit var bookmarklistViewModel: BookmarklistViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bookmarklistViewModel =
            ViewModelProviders.of(this).get(BookmarklistViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_bookmarklist, container, false)
        val textView: TextView = root.findViewById(R.id.text_bookmarklist)
        bookmarklistViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}