package com.enrech.articles.presentation.ui.article_detail.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.enrech.articles.R
import com.enrech.articles.databinding.FragmentArticleDetailBinding
import com.enrech.articles.presentation.ui.article_detail.viewmodel.ArticleDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailFragment: Fragment(R.layout.fragment_article_detail) {

    private var _binding: FragmentArticleDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArticleDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArticleDetailBinding.bind(view)
        initActionBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initActionBar() = binding.toolbar.apply {
        setNavigationIcon(R.drawable.ic_round_arrow_back_ios_24)
        setNavigationOnClickListener {
            (activity as? AppCompatActivity)?.onSupportNavigateUp()
        }
    }

}