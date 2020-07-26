package com.omkar.jet2demo.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Media : RealmObject() {
    @SerializedName("id")
    @Expose
    @PrimaryKey
    var id: Long? = null

    @SerializedName("blogId")
    @Expose
    var blogId: Long? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

}