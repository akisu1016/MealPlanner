package com.squarename.mealplanner

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.FileProvider
import com.squarename.mealplanner.tflite.Classifier
import com.squarename.mealplanner.tflite.Classifier.create
import com.squarename.mealplanner.ui.library.LibraryFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squarename.mealplanner.ui.bookmarklist.BkmListFragment
import com.squarename.mealplanner.ui.diary.DiaryViewPagerFragment
import com.squarename.mealplanner.ui.recyclerview.RecycleviewFragment
import java.io.File
import java.io.FileDescriptor
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var classifier: Classifier
    private lateinit var library: LibraryFragment
    private lateinit var searchView: SearchView
    val RESULT_IMAGEFILE = 1001
    val RESULT_CAMERAFILE = 1002

    // BottomNavigationViewのリスナー
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_diary -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, DiaryViewPagerFragment())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_library -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, LibraryFragment())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_bookmarklist -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, BkmListFragment())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//        val navController = findNavController(R.id.nav_host_fragment)
//        navView.setupWithNavController(navController)

        // BottomNavigationViewにリスナーを付ける
        val navigation: BottomNavigationView = findViewById(R.id.nav_view)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        // はじめに表示するFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, DiaryViewPagerFragment())
            .commit()

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
        searchView = seachItem.actionView as SearchView
        searchView.run {
            queryHint = context.getString(R.string.searchHint)
            setIconifiedByDefault(false)
            clearFocus()
        }

        //searchViewのリスナー
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            //検索ボタンを押した
            override fun onQueryTextSubmit(query: String?): Boolean {

                var newquery = query?.replace("　", ",")
                newquery = newquery?.replace(" ", ",")

                // 検索Fragmentを呼び出す
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, RecycleviewFragment.newInstance(newquery))
                    .commit()
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
        var resulttext: String? = ""
        Log.d("existresulutData", resultData.toString())

        resultData?.let { resultData ->
            if (resultCode == Activity.RESULT_OK) {
                //終了リザルトが画像選択アクテビティ
                if (newRequestCode == RESULT_IMAGEFILE) {
                    var itemcount = resultData?.clipData?.itemCount ?: 0
                    for (i in 0..itemcount - 1) {
                        val uri = resultData?.clipData?.getItemAt(i)?.uri
                        val pfDescriptor = contentResolver.openFileDescriptor(uri!!, "r")
                        val fileDescriptor: FileDescriptor = pfDescriptor!!.fileDescriptor
                        bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                        pfDescriptor.close()
                        results =
                            classifier.recognizeImage(bmp, 1)
                        resulttext += results[0].title + ","
                    }
                    //終了リザルトがカメラアクテビティ
                } else if (newRequestCode == RESULT_CAMERAFILE) {
                    resulttext += resultData.getStringExtra("data")
                }

                // 画像解析の結果で検索Fragmentを呼び出す
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, RecycleviewFragment.newInstance(resulttext))
                    .commit()
                resulttext = resulttext?.replace(",", " ")
                searchView.setQuery(resulttext, false)
            }
        }
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
