package com.omkar.jet2demo.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.omkar.jet2demo.R
import com.omkar.jet2demo.data.model.Article
import com.omkar.jet2demo.databinding.ArticleListItemBinding

class ArticleListAdapter(private val articleDataModels: List<Article>?) :
    RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ArticleListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.article_list_item,
            null,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (articleDataModels != null) {
            val article = articleDataModels[position]
            holder.binding.model = article
            if (article.media.size > 0)
                holder.binding.mediaModel = article.media[0]
            if (article.user.size > 0)
                holder.binding.userModel = article.user[0]!!

        }
    }

    override fun getItemCount(): Int {
        return articleDataModels?.size ?: 0
    }


    inner class ViewHolder internal constructor(val binding: ArticleListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}