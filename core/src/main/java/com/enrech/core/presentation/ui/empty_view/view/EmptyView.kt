package com.enrech.core.presentation.ui.empty_view.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.enrech.core.databinding.EmptyViewBinding
import com.enrech.core.presentation.ui.empty_view.model.EmptyVo

class EmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: EmptyViewBinding =
        EmptyViewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    fun fillViews(emptyVoEnum: EmptyVo) = with(binding) {
        imageView.setImageResource(emptyVoEnum.imageId)
        context?.let { safeContext ->
            titleTextView.text = safeContext.getString(emptyVoEnum.titleId)
            descriptionTextView.text = emptyVoEnum.descriptionId?.let { descriptionId ->
                safeContext.getString(descriptionId)
            }
            if (emptyVoEnum.retryButtonEnabled) {
                emptyVoEnum.retryButtonCustomTitleId?.let { titleId -> retryButton.setText(titleId) }
                setOnClickListener(emptyVoEnum)
            }
            retryButton.isVisible = emptyVoEnum.retryButtonEnabled
        }
    }

    private fun setOnClickListener(emptyVoEnum: EmptyVo) = with(binding) {
        retryButton.setOnClickListener { emptyVoEnum.retryCallback?.invoke() }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean = true
}