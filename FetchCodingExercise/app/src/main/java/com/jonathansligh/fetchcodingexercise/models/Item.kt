package com.jonathansligh.fetchcodingexercise.models

import com.google.gson.annotations.SerializedName

data class Item (
    @SerializedName("id") var id : Int?,
    @SerializedName("listId") var listId : Int?,
    @SerializedName("name") var name : String? = null
)