package com.SquareName.mealplanner.Realms

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Task: RealmObject() {
    @PrimaryKey open var id: String = UUID.randomUUID().toString()
    open var imageId: String = ""
    open var recipeName: String = ""
    open var recipeUrl: String = ""
    open var meal: String = ""
    open var timeStamp: Date = Date(System.currentTimeMillis())
}//ここでDBの要素を定義する