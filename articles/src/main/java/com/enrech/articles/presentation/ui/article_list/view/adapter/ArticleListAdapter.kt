package com.enrech.articles.presentation.ui.article_list.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.enrech.articles.databinding.ArticleListItemBinding
import com.enrech.articles.presentation.ui.article_list.model.SimpleArticleVo

class ArticleListAdapter(private val onArticleClickListener: (articleId: Int) -> Unit) :
    ListAdapter<SimpleArticleVo, ArticleItemHolder>(
        DIFF_CALLBACK
    ) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SimpleArticleVo>() {
            override fun areItemsTheSame(
                oldItem: SimpleArticleVo,
                newItem: SimpleArticleVo
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: SimpleArticleVo,
                newItem: SimpleArticleVo
            ): Boolean = oldItem.id == newItem.id

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleItemHolder {
        val itemView = ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArticleItemHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { onArticleClickListener(item.id) }
    }
}