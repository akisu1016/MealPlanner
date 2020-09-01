package com.squarename.mealplanner.rmethods

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

open class testTask: RealmObject() {
    @PrimaryKey open var id: String = UUID.randomUUID().toString()
    open var BkmorDia: Boolean = true
    open var title: String = ""
    open var url: String = ""
    open var timeStamp: String = dateFormat()
}//ここでDBの要素を定義する

open class Task: RealmObject(){
    @PrimaryKey open var id: String = UUID.randomUUID().toString()
    open var BkmorDia: Boolean =true
    open var title: String=""
    open var url: String=""
    open var material: String=""
    open var imgUrl: String=""
    open var timeStamp: String = dateFormat()
}

private  fun dateFormat(): String{
    var df:DateFormat = SimpleDateFormat("yyyy/MM/dd")
    var tmStampFormat: Date = Date(System.currentTimeMillis())
    return df.format(tmStampFormat)
}
