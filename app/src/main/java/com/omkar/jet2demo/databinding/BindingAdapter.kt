package com.omkar.jet2demo.databinding

import android.text.util.Linkify
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.omkar.jet2demo.R
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


@BindingAdapter("app:data")
fun getString(view: AppCompatTextView, value: Int) {
    val df = DecimalFormat("#.#")
    var data = ""
    when (view.id) {
        R.id.commentsTextView -> {
            data = if (value > 1000) {
                df.format(value / 1000.00) + "K Comments"
            } else {
                "$value Comments"
            }
        }
        R.id.likesTextView -> {
            data = if (value > 1000) {
                df.format(value / 1000.00) + "K Likes"
            } else {
                "$value Likes"
            }
        }
    }
    view.text = data
}


@BindingAdapter("app:image")
fun bindImage(
    imageView: AppCompatImageView,
    url: String?
) {
    if (url != null && !url.endsWith("/")) {
        Glide.with(imageView.context).load(url).apply(RequestOptions.circleCropTransform())
            .apply(
                RequestOptions().placeholder(R.mipmap.default_user)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            ).into(imageView)
    } else {
        Glide.with(imageView.context).clear(imageView)
    }
}


@BindingAdapter("app:articleImage")
fun bindArticleImage(
    imageView: AppCompatImageView,
    url: String?
) {
    if (url != null && !url.endsWith("/")) {
        Glide.with(imageView.context).load(url)
            .apply(
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            ).into(imageView)
    } else {
        Glide.with(imageView.context).clear(imageView)
    }
}

@BindingAdapter("app:date")
fun bindDate(
    view: AppCompatTextView,
    oldTime: String
) {
    var day = 0L
    var hh = 0L
    var mm = 0L
    try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val oldDate: Date? = dateFormat.parse(oldTime)
        val cDate = Date()
        val timeDiff = cDate.time - oldDate!!.time
        day = TimeUnit.MILLISECONDS.toDays(timeDiff)
        hh = (TimeUnit.MILLISECONDS.toHours(timeDiff) - TimeUnit.DAYS.toHours(day))
        mm = (TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(
            TimeUnit.MILLISECONDS.toHours(timeDiff)
        ))

    } catch (e: Exception) {
        e.printStackTrace()
        view.text = ""
    }

    if (mm <= 60 && hh >= 0) {
        if (hh <= 60 && day > 0) {
            view.text = "$day d"
        } else {
            view.text = "$hh hr"
        }
    } else {
        view.text = "$mm min"
    }

}

@BindingAdapter("app:linkify")
fun bindLinkify(view: AppCompatTextView, value: String) {
    view.text = value
    Linkify.addLinks(view, Linkify.WEB_URLS)
}