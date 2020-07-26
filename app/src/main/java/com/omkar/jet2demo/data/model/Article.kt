package com.omkar.jet2demo.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Article : RealmObject() {
    @SerializedName("id")
    @Expose
    @PrimaryKey
    var id: Long? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("content")
    @Expose
    var content: String? = null

    @SerializedName("comments")
    @Expose
    var comments: Int? = null

    @SerializedName("likes")
    @Expose
    var likes: Int? = null

    @SerializedName("media")
    @Expose
    var media: RealmList<Media> = RealmList()

    @SerializedName("user")
    @Expose
    var user: RealmList<User> = RealmList()

}