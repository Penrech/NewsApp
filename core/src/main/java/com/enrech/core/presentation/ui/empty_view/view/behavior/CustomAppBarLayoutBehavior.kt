package com.enrech.core.presentation.ui.empty_view.view.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class CustomAppBarLayoutBehavior @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : AppBarLayout.Behavior(context, attrs) {

    var shouldScroll: Boolean = false

    override fun onStartNestedScroll(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int,
        type: Int
    ): Boolean = shouldScroll

    override fun onTouchEvent(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        ev: MotionEvent
    ): Boolean = if (shouldScroll) {
        super.onTouchEvent(parent, child, ev)
    } else {
        false
    }

}