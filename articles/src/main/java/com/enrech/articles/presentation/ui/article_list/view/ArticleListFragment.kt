package com.enrech.articles.presentation.ui.article_list.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.enrech.articles.R
import com.enrech.articles.databinding.FragmentArticleListBinding
import com.enrech.articles.presentation.ui.article_list.model.SimpleArticleVo
import com.enrech.articles.presentation.ui.article_list.state.ArticleListViewState
import com.enrech.articles.presentation.ui.article_list.view.adapter.ArticleListAdapter
import com.enrech.articles.presentation.ui.article_list.viewmodel.ArticleListViewModel
import com.enrech.core.presentation.ui.empty_view.model.EmptyVo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        initStateObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initStateObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.viewState.collectLatest { viewState ->
                        when(viewState) {
                            is ArticleListViewState.Loading -> {}
                            is ArticleListViewState.Success -> handleSuccess(viewState.data)
                            is ArticleListViewState.ErrorOrEmpty -> configureEmptyView(viewState.emptyVo)
                        }
                        showLoading(viewState is ArticleListViewState.Loading)
                        showEmptyView(viewState is ArticleListViewState.ErrorOrEmpty)
                        showListContent(viewState is ArticleListViewState.Success)
                    }
                }
            }
        }
    }

    private fun initArticleRecyclerView() = with(binding.articlesRecyclerView) {
        adapter = ArticleListAdapter(::onArticleClicked)
    }

    private fun onArticleClicked(articleId: Int) {
        navController.navigate(ArticleListFragmentDirections.fromListToDetailAction(articleId))
    }

    private fun showLoading(show: Boolean) = with(binding.progressbar) {
        isVisible = show
    }

    private fun showListContent(show: Boolean) = with(binding.articlesRecyclerView) {
        isVisible = show
    }

    private fun showEmptyView(show: Boolean) = with(binding.emptyView) {
        isVisible = show
    }

    private fun handleSuccess(data: List<SimpleArticleVo>) = with(binding.articlesRecyclerView) {
        (adapter as? ArticleListAdapter)?.submitList(data)
    }

    private fun configureEmptyView(emptyVo: EmptyVo) = with(binding.emptyView) {
        this.fillViews(emptyVo)
    }

}