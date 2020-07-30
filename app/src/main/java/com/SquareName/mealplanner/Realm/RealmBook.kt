package com.SquareName.mealplanner.Realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.UUID

class RealmBook: RealmObject() {
    @PrimaryKey open var id : String = UUID.randomUUID().toString()
    @Required open var name : String = ""
    open var price : Long = 0
}