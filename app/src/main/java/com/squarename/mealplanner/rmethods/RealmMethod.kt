package com.squarename.mealplanner.rmethods

import android.util.Log
import io.realm.Realm
import java.util.*
import com.squarename.mealplanner.getrecipe.Item

class RealmMethod {
    
    open val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    fun create(BkmorDia: Boolean, title:String = "", url:String = ""){
        realm.executeTransaction{
            var task = realm.createObject(testTask::class.java, UUID.randomUUID().toString())
            task.BkmorDia = BkmorDia
            task.title = title
            task.url = url
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
    fun readFromTime(timeStamp: String): List<Item>{//時間から記録用のデータをList<Item>型で返す
        var task = realm.where(testTask::class.java)
            .equalTo("BkmorDia",false)
            .equalTo("timeStamp",timeStamp)
            .findAll()
        val listTask: List<testTask> = task
        var items = mutableListOf<Item>()
        for(i in listTask.indices){
            items.add(i, Item(listTask[i].id, listTask[i].title, listTask[i].url))
        }
        return items
    }
    //タイムスタンプをyyyy/MM/ddで返す
    fun getTime(timeStamp:String): String{
        var task = realm.where(testTask::class.java)
            .equalTo("timeStamp",timeStamp)
            .findAll()
        val listTask: List<testTask> = task
        Log.d("listcheck", "Date:" + listTask[0].timeStamp)
        return listTask[0].timeStamp
    }


//    fun create(imageId:String = "", recipeName:String = "", recipeUrl:String="", meal:String=""){
//        realm.executeTransaction {
//            var task = realm.createObject(Task::class.java , UUID.randomUUID().toString())
//            task.imageId = imageId
//            task.recipeName = recipeName
//            task.recipeUrl = recipeUrl
//            task.meal = meal
//        }
//    }

    fun delete(id: String) {
        realm.executeTransaction {
            val task = realm.where(testTask::class.java)
                .equalTo("id", id)
                .findFirst()
                ?: return@executeTransaction
            task.deleteFromRealm()
        }
    }

    fun deleteAll() {
        realm.executeTransaction {
            realm.deleteAll()
        }
    }
}