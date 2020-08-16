package com.squarename.mealplanner.getrecipe

// test用
data class Item(var id: String,
                var title: String,
                var url: String)

data class Recipe(
    var id: String,
    var title: String,
    var url: String,
    var material: String,
    var imgUrl: String)
