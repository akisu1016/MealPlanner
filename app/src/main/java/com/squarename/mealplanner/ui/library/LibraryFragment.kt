package com.squarename.mealplanner.ui.library

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squarename.mealplanner.R
import kotlinx.android.synthetic.main.fragment_library.view.*

class LibraryFragment : Fragment() {

    val RESULT_IMAGEFILE = 1001
    val RESULT_CAMERAFILE = 1002

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_library, container, false)


        view.camera_image.setOnClickListener{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, RESULT_CAMERAFILE)
        }

        view.folder_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, RESULT_IMAGEFILE)
        }

        return view
    }

}