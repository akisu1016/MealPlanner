package com.SquareName.mealplanner.Realm

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import java.util.UUID

class RealmApp: android.app.Application() {
    override fun onCreate() {
        super.onCreate()

        // Realmの初期化
        Realm.init(this)
        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)
    }

    //処理用のfunction
    lateinit var realm: Realm

    fun realmcreate(name:String, price:Long = 0){

        realm.executeTransaction {
            var book = realm.createObject(RealmBook::class.java , UUID.randomUUID().toString())
            book.name = name
            book.price = price
            realm.copyToRealm(book)
        }
    }

    fun realmread() : RealmResults<RealmBook> {
        return realm.where(RealmBook::class.java).findAll()
    }

    fun realmupdate(id:String, name:String, price:Long = 0){
        realm.executeTransaction {
            var book = realm.where(RealmBook::class.java).equalTo("id",id).findFirst()
            book!!.name = name
            if(price != 0.toLong()) {
                book.price = price
            }
        }
    }

    fun realmdelete(id:String){
        realm.executeTransaction {
            var book = realm.where(RealmBook::class.java).equalTo("id",id).findAll()
            book.deleteFromRealm(0)
        }
    }
}