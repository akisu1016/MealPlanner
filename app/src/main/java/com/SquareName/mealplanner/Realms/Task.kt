package com.SquareName.mealplanner.Realms

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

open class testTask: RealmObject() {
    @PrimaryKey open var id: String = UUID.randomUUID().toString()
    open var title: String = ""
    open var url: String = ""
    open var tag: Boolean = true
    open var timeStamp: Date = Date(System.currentTimeMillis())
    open var TSformat: String = dateFormat()
}//ここでDBの要素を定義する

open class Task: RealmObject(){
    @PrimaryKey open var id: String = UUID.randomUUID().toString()
    open var imageId: String = ""
    open var recipeName: String = ""
    open var recipeUrl: String = ""
    open var meal: String = ""
    open var timeStamp: Date = Date(System.currentTimeMillis())
    open var TSformat: String = dateFormat()
}

private  fun dateFormat(): String{
    var df:DateFormat = SimpleDateFormat("yyyy/MM/dd")
    var tmStampFormat: Date = Date(System.currentTimeMillis())
    return df.format(tmStampFormat)
}

data class taskData(
    var title: String,
    var url: String,
    var tag: Boolean,
    var timeStamp: Date = Date(System.currentTimeMillis()),
    var TSformat: String = dateFormat()
)