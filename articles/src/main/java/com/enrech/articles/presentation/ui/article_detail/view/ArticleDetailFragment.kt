package com.enrech.articles.presentation.ui.article_detail.view

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.enrech.articles.R
import com.enrech.articles.databinding.FragmentArticleDetailBinding
import com.enrech.articles.presentation.ui.article_detail.model.DetailedArticleVo
import com.enrech.articles.presentation.ui.article_detail.state.ArticleDetailViewState
import com.enrech.articles.presentation.ui.article_detail.viewmodel.ArticleDetailViewModel
import com.enrech.core.presentation.ui.empty_view.model.EmptyVo
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleDetailFragment: Fragment(R.layout.fragment_article_detail) {

    private var _binding: FragmentArticleDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArticleDetailViewModel by viewModels()

    private val args: ArticleDetailFragmentArgs by navArgs()

    private val navController: NavController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArticleDetailBinding.bind(view)
        loadArticleDetails()
        initActionBar()
        initStateObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadArticleDetails() {
        viewModel.loadDetailsWithId(args.articleId)
    }

    private fun initActionBar() = with(binding) {
        toolbar.apply {
            setNavigationIcon(R.drawable.ic_round_arrow_back_ios_24)
            context?.let { safeContext ->
                navigationIcon?.setTint(
                    ContextCompat.getColor(
                        safeContext,
                        R.color.white
                    )
                )
            }
            setNavigationOnClickListener {
                navController.navigateUp()
            }
        }
    }

    private fun initStateObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.viewState.collectLatest { viewState ->
                        when(viewState) {
                            is ArticleDetailViewState.Loading -> {}
                            is ArticleDetailViewState.Success -> handleSuccess(viewState.data)
                            is ArticleDetailViewState.Error -> configureEmptyView(viewState.emptyVo)
                        }
                        showLoading(viewState is ArticleDetailViewState.Loading)
                        showEmptyView(viewState is ArticleDetailViewState.Error)
                        showContent(viewState is ArticleDetailViewState.Success)
                    }
                }
            }
        }
    }

    private fun showLoading(show: Boolean) = with(binding.progressbar) {
        isVisible = show
    }

    private fun showContent(show: Boolean) = with(binding.articleContentContainer) {
        isVisible = show
    }

    private fun showEmptyView(show: Boolean) = with(binding.emptyView) {
        isVisible = show
    }

    private fun handleSuccess(data: DetailedArticleVo) = with(binding) {
        collapsingToolbar.title = data.title
        dateTextView.text = data.date
        subtitleTextView.text = data.subtitle
        bodyTextView.text = data.body
    }

    private fun configureEmptyView(emptyVo: EmptyVo) = with(binding.emptyView) {
        this.fillViews(emptyVo)
    }

    private fun enableToolbarScroll(enable: Boolean) = with(binding.collapsingToolbar) {
        (layoutParams as? AppBarLayout.LayoutParams)?.scrollFlags =
            if (enable) {
                SCROLL_FLAG_SCROLL or SCROLL_FLAG_EXIT_UNTIL_COLLAPSED or SCROLL_FLAG_SNAP
            } else 0
    }

}