package com.SquareName.mealplanner

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.SquareName.mealplanner.tflite.Classifier
import com.SquareName.mealplanner.tflite.Classifier.create
import com.SquareName.mealplanner.ui.Libarary.LibararyFragment
import kotlinx.android.synthetic.main.fragment_library.*
import java.io.FileDescriptor
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var classifier: Classifier
    private lateinit var Libarary: LibararyFragment
    val RESULT_IMAGEFILE = 1001
    val RESULT_CAMERAFILE = 1002


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_diary, R.id.navigation_library, R.id.navigation_bookmarklist
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        try {
            Libarary = LibararyFragment()
            classifier = create(this, Classifier.Device.CPU, 2)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        val newRequestCode = requestCode and 0xffff
        lateinit var results:List<Classifier.Recognition>
        var text: String? = ""
        textView = findViewById<TextView>(R.id.resulttextView)
        imageView = findViewById<ImageView>(R.id.imageView)

        //終了リザルトが画像選択アクテビティ
        if (newRequestCode == RESULT_IMAGEFILE && resultCode == Activity.RESULT_OK && resultData != null) {
            var uri: Uri? = resultData.data
            var pfDescriptor = getContentResolver().openFileDescriptor(uri!!, "r")
            val fileDescriptor: FileDescriptor = pfDescriptor!!.fileDescriptor
            val bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            pfDescriptor.close()

            this.imageView.setImageBitmap(resizeImage(bmp))

            results =
                classifier.recognizeImage(resizeImage(bmp), 1)
            text = results[0].title

        //終了リザルトがカメラアクテビティ
        } else if (newRequestCode == RESULT_CAMERAFILE && resultCode == Activity.RESULT_OK && resultData != null) {
            val bmp: Bitmap
            if (resultData.extras == null) {
                return
            } else {
                bmp = resultData.extras!!["data"] as Bitmap

                this.imageView.setImageBitmap(resizeImage(bmp))
                results =
                    classifier.recognizeImage(resizeImage(bmp), 1)
                text += results[0].title
            }
        }

        this.resulttextView.text = text
    }


    //ビットマップイメージをリサイズ
    fun resizeImage(bmp: Bitmap): Bitmap {
        var height = bmp.height
        var width = bmp.width
        while (true) {
            var i = 2
            if (width < 500 && height < 500) {
                break
            } else {
                if (width > 500 || height > 500) {
                    width = width / i
                    height = height / i
                } else {
                    break
                }
                i++
            }
        }

        var croppedBitmap =
            Bitmap.createScaledBitmap(bmp, width, height, false)

        return croppedBitmap

    }
}