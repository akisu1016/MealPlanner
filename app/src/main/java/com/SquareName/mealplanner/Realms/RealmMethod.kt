package com.SquareName.mealplanner.Realms

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.SquareName.mealplanner.ui.Recyclerview.RecyclerAdapter
import io.realm.Realm
import java.util.*

class RealmMethod {

    private lateinit var groupeAdapter: RecyclerView.Adapter<*>
    open val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    //本チャンはこっち（多分）
    fun create(imageId:String = "", recipeName:String = "", recipeUrl:String="", meal:String=""){
        realm.executeTransaction {
            var task = realm.createObject(Task::class.java , UUID.randomUUID().toString())
            task.imageId = imageId
            task.recipeName = recipeName
            task.recipeUrl = recipeUrl
            task.meal = meal
        }
    }
    fun testCreate(title:String = "", url:String = "", BkmorDia: Boolean){
        realm.executeTransaction{
            var task = realm.createObject(testTask::class.java, UUID.randomUUID().toString())
            task.title = title
            task.url = url
            task.tag = BkmorDia
            Log.d("DE InputCheck","cre")

        }
    }

    fun read(BkmorDia: Boolean){//true->BookMark  false->Diary
        var task = realm.where(testTask::class.java).equalTo("tag",BkmorDia).findAll()
        Log.d("InputCheck", task.toString())
    }

    fun delete(id: String) {
        realm.executeTransaction {
            val task = realm.where(testTask::class.java).equalTo("id", id).findFirst()
                ?: return@executeTransaction
            task.deleteFromRealm()
            Log.d("DE InputCheck","del")
        }
    }

    fun deleteAll() {
        realm.executeTransaction {
            realm.deleteAll()
        }
    }
}