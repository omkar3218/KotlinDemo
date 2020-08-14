package com.omkar.jet2demo.ui.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.omkar.jet2demo.R
import com.omkar.jet2demo.data.model.Image
import com.omkar.jet2demo.databinding.ArticleListItemUpBinding

class ArticleListAdapter(
    private val articleDataModels: List<Image>?,
    private var context: ArticleListFragment
) :
    RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ArticleListItemUpBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.article_list_item_up,
            null,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.userImageView.setImageDrawable(null)
        if (articleDataModels != null) {
            holder.binding.imageLink = articleDataModels[position].link
        }
        holder.binding.userImageView.setOnClickListener {
            articleDataModels?.get(position)?.let { it1 -> context.navigateToImageDetailsScreen(it1) }
        }
    }

    override fun getItemCount(): Int {
        return articleDataModels?.size ?: 0
    }


    inner class ViewHolder internal constructor(val binding: ArticleListItemUpBinding) :
        RecyclerView.ViewHolder(binding.root)

}