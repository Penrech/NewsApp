package com.enrech.articles.presentation.ui.article_detail.view

import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
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
import com.enrech.core.presentation.ui.empty_view.view.behavior.CustomAppBarLayoutBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ArticleDetailFragment: Fragment(R.layout.fragment_article_detail) {

    private var _binding: FragmentArticleDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArticleDetailViewModel by viewModels()

    private val args: ArticleDetailFragmentArgs by navArgs()

    private val navController: NavController by lazy { findNavController() }

    @Inject lateinit var animationBuilder: ArticleDetailColorAnimationBuilder

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
            setNavigationIconColor(R.color.design_default_color_primary)
            setNavigationOnClickListener {
                navController.navigateUp()
            }
        }
        appbar.setLiftable(false)
        appbar.setLifted(false)
    }

    private fun setNavigationIconColor(@ColorRes color: Int) = with(binding.toolbar) {
        context?.let { safeContext ->
            navigationIcon?.setTint(
                ContextCompat.getColor(
                    safeContext,
                    color
                )
            )
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

    private fun configureEmptyView(emptyVo: EmptyVo) = with(binding.emptyView) {
        this.fillViews(emptyVo)
    }

    private fun handleSuccess(data: DetailedArticleVo) = with(binding) {
        collapsingToolbar.title = data.title
        dateTextView.text = data.date
        subtitleTextView.text = data.subtitle
        bodyTextView.text = data.body
        animateOnSuccess()
    }

    private fun animateOnSuccess() = with(binding) {
        animationBuilder
            .animateNavigationIconColor(toColorId = R.color.white) { animatedColor ->
                toolbar.navigationIcon?.setTint(animatedColor)
            }
            .animateTitleTextColor(toColorId = R.color.white) { animatedColor ->
                collapsingToolbar.setCollapsedTitleTextColor(animatedColor)
                collapsingToolbar.setExpandedTitleColor(animatedColor)
            }
            .animateAppBarBackgroundColor(toColorId = R.color.design_default_color_primary) { animatedColor ->
                appbar.setBackgroundColor(animatedColor)
            }
            .startAnimation()

        enableToolbarScroll(true)
        appbar.setExpanded(true, true)
    }

    private fun enableToolbarScroll(enable: Boolean) = with(binding) {
        ((appbar.layoutParams as? CoordinatorLayout.LayoutParams)?.behavior as? CustomAppBarLayoutBehavior)?.shouldScroll =
            enable
    }

}