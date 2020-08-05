package com.SquareName.mealplanner

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.SquareName.mealplanner.tflite.Classifier
import com.SquareName.mealplanner.tflite.Classifier.create
import com.SquareName.mealplanner.ui.Library.LibraryFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.FileDescriptor
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var classifier: Classifier
    private lateinit var library: LibraryFragment
    val RESULT_IMAGEFILE = 1001
    val RESULT_CAMERAFILE = 1002


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        // 各メニューIDを一連のIDとして渡すと、
        // メニューは最上位の目的地と見なされます。
        /*//タイトルバー表示
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_diary, R.id.navigation_library, R.id.navigation_bookmarklist
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration) */
        navView.setupWithNavController(navController)

        try {
            library = LibraryFragment()
            classifier = create(this, Classifier.Device.CPU, 2)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    //アクションバーの設定
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.actionbar, menu)

        supportActionBar?.title = ""
        val seachItem = menu.findItem(R.id.menu_search)
        val searchView = seachItem.actionView as SearchView
        searchView.run {
            queryHint = context.getString(R.string.searchHint)
            setIconifiedByDefault(false)
            clearFocus()
        }

        //searchViewのリスナー
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            //検索ボタンを押した
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            //テキストに変更がかかった
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isNotEmpty()) {

                } else {

                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    //別アクティビティから戻ってきたときの処理　リクエストコードで認識する
    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        val newRequestCode = requestCode and 0xffff
        lateinit var bmp: Bitmap
        lateinit var results: List<Classifier.Recognition>
        var text: String? = ""
        textView = findViewById(R.id.result_textView)
        imageView = findViewById(R.id.imageView)

        resultData?.let { resultData ->
            if (resultCode == Activity.RESULT_OK) {
                //終了リザルトが画像選択アクテビティ
                if (newRequestCode == RESULT_IMAGEFILE) {
                    var uri: Uri? = resultData.data
                    var pfDescriptor = contentResolver.openFileDescriptor(uri!!, "r")
                    val fileDescriptor: FileDescriptor = pfDescriptor!!.fileDescriptor
                    bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                    pfDescriptor.close()

                    //終了リザルトがカメラアクテビティ
                } else if (newRequestCode == RESULT_CAMERAFILE) {
                    resultData.extras?.let {
                        bmp = it["data"] as Bitmap
                    }
                }
                this.imageView.setImageBitmap(resizeImage(bmp))
                results =
                    classifier.recognizeImage(resizeImage(bmp), 1)
                text += results[0].title
                this.textView.text = text
            }
        }
        this.textView.requestFocus()
    }

    //ビットマップイメージをリサイズ
    fun resizeImage(bmp: Bitmap): Bitmap {
        var height = bmp.height
        var width = bmp.width
        var i = 2
        while (true) {
            if (width < 500 && height < 500) {
                break
            } else {
                if (width > 500 || height > 500) {
                    width /= i
                    height /= i
                    i++
                } else {
                    break
                }
            }
        }

        var croppedBitmap =
            Bitmap.createScaledBitmap(bmp, width, height, false)

        return croppedBitmap
    }
}
