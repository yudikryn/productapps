package com.yudikryn.productapps.ui.detail

import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yudikryn.productapps.R
import com.yudikryn.productapps.data.remote.model.Product
import com.yudikryn.productapps.databinding.ActivityDetailBinding
import com.yudikryn.productapps.helper.FormatHelper
import java.util.Locale

class DetailActivity  : AppCompatActivity(R.layout.activity_detail) {

    private val binding by viewBinding(ActivityDetailBinding::bind)

    private val detailAdapter by lazy {
        DetailAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initIntent()
    }

    private fun initIntent(){
        val dataProduct = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(PRODUCT_EXTRAS, Product::class.java)
        } else {
            intent.getParcelableExtra(PRODUCT_EXTRAS)
        }

        dataProduct?.let{
            binding.tvTitle.text = it.title
            binding.tvDescription.text = it.description
            binding.tvBrand.setValue(it.brand.capitalize(Locale.getDefault()))
            binding.tvStock.setValue(it.stock.toString())
            binding.tvCategory.setValue(it.category.capitalize(Locale.getDefault()))

            binding.ratingBar.rating = it.rating.toFloat()
            binding.ratingBar.isEnabled = false

            val afterDiscount = calculateDiscountedPrice(it.price, it.discountPercentage)

            val formattedOriginalPrice = FormatHelper.formatDollar(it.price)
            val formattedDiscountedPrice = FormatHelper.formatDollar(afterDiscount)

            binding.tvPrice.text  = formattedOriginalPrice
            binding.tvPrice.paintFlags = binding.tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            if (it.discountPercentage != 0.0){
                binding.tvDiscountPrice.text  = formattedDiscountedPrice
            }

            Glide.with(this@DetailActivity)
                .load(it.thumbnail)
                .apply(
                    RequestOptions.placeholderOf(R.color.black)
                        .error(R.color.black)
                )
                .into(binding.ivProduct)

            detailAdapter.submitList(it.images)
            binding.rvProduct.apply {
                layoutManager = GridLayoutManager(this@DetailActivity, 3)
                setHasFixedSize(true)
                adapter = detailAdapter
            }
        }
    }

    private fun calculateDiscountedPrice(originalPrice: Double, discountPercentage: Double): Double {
        val discountAmount = originalPrice * (discountPercentage / 100)
        return originalPrice - discountAmount
    }

    companion object{
        const val PRODUCT_EXTRAS = "product-extras"
    }
}