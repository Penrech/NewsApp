package com.enrech.articles.presentation.ui.article_list.view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.enrech.articles.databinding.ArticleListItemBinding
import com.enrech.articles.presentation.ui.article_list.model.SimpleArticleVo

class ArticleItemHolder(private val binding: ArticleListItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(articleItem: SimpleArticleVo) = with(binding) {
        titleTextView.text = articleItem.title
        subtitleTextView.text = articleItem.subtitle
        dateTextView.text = articleItem.date
    }
}