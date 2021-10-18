package com.enrech.articles.presentation.ui.article_detail.view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.enrech.articles.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ArticleDetailColorAnimationBuilder @Inject constructor(@ApplicationContext private val context: Context) {

    private val animationList: MutableList<Animator> = mutableListOf()

    fun animateNavigationIconColor(
        @ColorRes fromColorId: Int = R.color.design_default_color_primary,
        @ColorRes toColorId: Int,
        animationCallback: (color: Int) -> Unit
    ) = animateColor(fromColorId, toColorId, animationCallback)

    fun animateTitleTextColor(
        @ColorRes fromColorId: Int = R.color.design_default_color_primary,
        @ColorRes toColorId: Int,
        animationCallback: (color: Int) -> Unit
    ) = animateColor(fromColorId, toColorId, animationCallback)

    fun animateAppBarBackgroundColor(
        @ColorRes fromColorId: Int = android.R.color.transparent,
        @ColorRes toColorId: Int,
        animationCallback: (color: Int) -> Unit
    ) = animateColor(fromColorId, toColorId, animationCallback)

    private fun animateColor(
        @ColorRes fromColorId: Int,
        @ColorRes toColorId: Int,
        animationCallback: (color: Int) -> Unit
    ): ArticleDetailColorAnimationBuilder {
        val fromColor = ContextCompat.getColor(context, fromColorId)
        val toColor = ContextCompat.getColor(context, toColorId)
        animationList.add(ValueAnimator.ofArgb(fromColor, toColor).apply {
            addUpdateListener {
                animationCallback(it.animatedValue as Int)
            }
        })
        return this
    }

    fun startAnimation() {
        AnimatorSet().apply {
            playTogether(animationList)
            start()
        }
    }
}