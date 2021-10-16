package com.enrech.articles.presentation.ui.article_list.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.enrech.articles.R
import com.enrech.articles.databinding.FragmentArticleListBinding
import com.enrech.articles.presentation.ui.article_list.view.adapter.ArticleListAdapter
import com.enrech.articles.presentation.ui.article_list.viewmodel.ArticleListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleListFragment : Fragment(R.layout.fragment_article_list) {

    private var _binding: FragmentArticleListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArticleListViewModel by viewModels()

    private val navController: NavController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArticleListBinding.bind(view)
        initArticleRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initArticleRecyclerView() = with(binding.articlesRecyclerView) {
        adapter = ArticleListAdapter(::onArticleClicked)
    }

    private fun onArticleClicked(articleId: Int) {
        navController.navigate(ArticleListFragmentDirections.fromListToDetailAction(articleId))
    }

}