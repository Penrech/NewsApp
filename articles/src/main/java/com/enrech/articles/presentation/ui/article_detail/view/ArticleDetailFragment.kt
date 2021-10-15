package com.enrech.articles.presentation.ui.article_detail.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.enrech.articles.R
import com.enrech.articles.databinding.FragmentArticleDetailBinding
import com.enrech.articles.presentation.ui.article_detail.viewmodel.ArticleDetailViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.*
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

    private fun initActionBar() = with(binding) {
        collapsingToolbar.title = "Article 1"
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
                (activity as? AppCompatActivity)?.onBackPressed()
            }
        }
    }

    private fun enableToolbarScroll(enable: Boolean) = with(binding.collapsingToolbar) {
        (layoutParams as? AppBarLayout.LayoutParams)?.scrollFlags =
            if (enable) {
                SCROLL_FLAG_SCROLL or SCROLL_FLAG_EXIT_UNTIL_COLLAPSED or SCROLL_FLAG_SNAP
            } else 0
    }

}