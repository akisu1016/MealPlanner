package com.squarename.mealplanner.rmethods

import android.app.Activity
import android.app.AlertDialog
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.realm.Realm
import java.util.*
import com.squarename.mealplanner.getrecipe.Item
import com.squarename.mealplanner.getrecipe.Recipe

class RealmMethod {
    
    open val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    fun create(BkmorDia: Boolean, title: String = "", url: String ="", imgUrl: String =""){
        realm.executeTransaction{
            var task = realm.createObject(Task::class.java, UUID.randomUUID().toString())
            task.BkmorDia = BkmorDia
            task.title = title
            task.url = url
            task.imgUrl = imgUrl
        }
    }

    fun readAll(BkmorDia: Boolean){//true->Bkm,false->Diary
        var task = realm.where(testTask::class.java)
            .equalTo("BkmorDia",BkmorDia)
            .findAll()
        val listTask: List<testTask> = task
        Log.d("InputCheck", listTask.toString())
    }

    //完全一致するレコードを探す（好きにいじって）
    fun readSerch(BkmorDia: Boolean, title: String, url: String){
        var task = realm.where(testTask::class.java)
            .equalTo("BkmorDia", BkmorDia)
            .equalTo("title", title)
            .equalTo("url", url)
    }

    fun rExist(BkmorDia: Boolean, url: String): Boolean{
        val task = realm.where(Task::class.java)
            .equalTo("BkmorDia",BkmorDia)
            .equalTo("url",url)
            .findAll()
        val list: List<Task> = task
        val items = mutableListOf<Recipe>()
        for(i in list.indices){
            items.add(i, Recipe(list[i].id, list[i].title, list[i].url,list[i].material,list[i].imgUrl))
        }
        return items.isEmpty()
    }

    //動作テスト用コード
    fun readFromTime(timeStamp: String): List<Recipe>{
        var task = realm.where(Task::class.java)
            .equalTo("BkmorDia",false)
            .equalTo("timeStamp",timeStamp)
            .findAll()
        val listTask: List<Task> = task
        var items = mutableListOf<Recipe>()
        for(i in listTask.indices){
            items.add(i, Recipe(listTask[i].id, listTask[i].title, listTask[i].url,listTask[i].material,listTask[i].imgUrl))
        }
        return items
    }

    fun readBkm(): List<Recipe>{
        var task = realm.where(Task::class.java)
            .equalTo("BkmorDia",true)
            .findAll()
        val listTask: List<Task> = task
        val items = mutableListOf<Recipe>()
        for(i in listTask.indices){
            items.add(i, Recipe(listTask[i].id, listTask[i].title, listTask[i].url,listTask[i].material,listTask[i].imgUrl))
        }
        return  items
    }

//    fun delete(id: String) {
//        realm.executeTransaction {
//            val task = realm.where(testTask::class.java)
//                .equalTo("id", id)
//                .findFirst()
//                ?: return@executeTransaction
//            task.deleteFromRealm()
//        }
//    }

    fun delete(BkmorDia: Boolean, url: String){
        var task = realm.where(Task::class.java)
            .equalTo("BkmorDia", BkmorDia)
            .equalTo("url",url)
            .findAll()
        //削除
        realm.executeTransaction {
            task.deleteAllFromRealm()
        }
    }

    fun deleteAll() {
        realm.executeTransaction {
            realm.deleteAll()
        }
    }


    //リスト長押し用のブックマーク書き換え関数(汚い)
    fun bkmDelDialog(url: String, act: Fragment){
        AlertDialog.Builder(act.activity) // FragmentではActivityを取得して生成
            .setTitle("BookMark")
            .setMessage("ブックマークを削除しますか？")
            .setPositiveButton("No", { dialog, which ->
            })
            .setNegativeButton("Yes", { dialog, which ->
                delete(true, url)
            })
            .show()
    }

    fun bkmCreDialog(title: String, url: String, imgUrl: String, act: Fragment){
        AlertDialog.Builder(act.activity) // FragmentではActivityを取得して生成
            .setTitle("BookMark")
            .setMessage("ブックマークに登録しますか？")
            .setPositiveButton("No", { dialog, which ->
            })
            .setNegativeButton("Yes", { dialog, which ->
                create(true, title, url, imgUrl)
            })
            .show()
    }

    fun bkmExistDialog(act: Fragment){
        AlertDialog.Builder(act.activity)
            .setTitle("BookMark")
            .setMessage("既にブックマーク済みです")
            .setPositiveButton("OK", {dialog,which->})
            .show()
    }
}