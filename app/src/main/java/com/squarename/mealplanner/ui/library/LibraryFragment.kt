package com.squarename.mealplanner.ui.library

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.squarename.mealplanner.R
import com.squarename.mealplanner.WebViewActivity
import kotlinx.android.synthetic.main.fragment_library.view.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class LibraryFragment : Fragment() {

    private lateinit var path: String
    val RESULT_IMAGEFILE = 1001
    val RESULT_CAMERAFILE = 1002

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_library, container, false)

        view.camera_image.setOnClickListener{
            val intent = Intent(activity, CameraActivity::class.java)
            startActivityForResult(intent, RESULT_CAMERAFILE)
        }

        view.folder_image.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_PICK
            this.startActivityForResult(Intent.createChooser(intent, "Choose Photo"),
                RESULT_IMAGEFILE)
        }

        return view
    }
}