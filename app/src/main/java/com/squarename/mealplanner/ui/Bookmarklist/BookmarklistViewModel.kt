package com.squarename.mealplanner.ui.Bookmarklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookmarklistViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is bookmarklist Fragment"
    }
    val text: LiveData<String> = _text
}