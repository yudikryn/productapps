package com.yudikryn.productapps.ui.detail

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import com.yudikryn.productapps.R
import com.yudikryn.productapps.databinding.ViewItemStatsBinding

class ItemStatsView : FrameLayout {

    lateinit var label: String

    private val binding = ViewItemStatsBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet, 0) {
        setup(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
            super(context, attributeSet, defStyleAttr) {
        setup(context, attributeSet)
    }

    private fun setup(context: Context, attrs: AttributeSet?) {
        getAttributeSet(context, attrs)
        setupView()
    }

    private fun getAttributeSet(context: Context, attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.ItemStatsViewAttrs) {
            label =
                getString(R.styleable.ItemStatsViewAttrs_ItemStatsViewLabel).orEmpty()
        }
    }

    private fun setupView() {
        binding.tvName.text = label
    }

    fun setValue(value: String) {
        binding.tvValue.text = value
    }
}