package com.yudikryn.productapps.ui.main

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yudikryn.productapps.R
import com.yudikryn.productapps.data.remote.model.Product
import com.yudikryn.productapps.databinding.ItemProductBinding
import com.yudikryn.productapps.helper.FormatHelper.formatDollar

class MainAdapter(private val onClick: (product: Product, action: String, position: Int) -> Unit) : ListAdapter<Product, MainAdapter.MyViewHolder>(DIFF_CALLBACK) {

    fun updateItem(product: Product, position: Int){
        currentList[position].isFavorite = product.isFavorite
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product, position, onClick)
    }

    class MyViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(product: Product, position: Int, onClick: (product: Product, action: String, position: Int) -> Unit) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(product.thumbnail)
                    .apply(
                        RequestOptions.placeholderOf(R.color.black)
                            .error(R.color.black)
                    )
                    .into(binding.ivProduct)

                tvTitle.text = product.title
                ratingBar.rating = product.rating.toFloat()
                ratingBar.isEnabled = false

                if (product.isFavorite){
                    ivFavorite.setColorFilter(ContextCompat.getColor(itemView.context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY)
                }else{
                    ivFavorite.setColorFilter(ContextCompat.getColor(itemView.context, R.color.grey), android.graphics.PorterDuff.Mode.MULTIPLY)
                }

                val afterDiscount = calculateDiscountedPrice(product.price, product.discountPercentage)

                val formattedOriginalPrice = formatDollar(product.price)
                val formattedDiscountedPrice = formatDollar(afterDiscount)

                tvPrice.text = formattedOriginalPrice
                tvPrice.paintFlags = tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                if (product.discountPercentage != 0.0){
                    tvDiscountPrice.text = formattedDiscountedPrice
                }

                itemView.setOnClickListener {
                    onClick.invoke(product, ACTION_DETAIL, position)
                }

                ivFavorite.setOnClickListener {
                    if (!product.isFavorite){
                        onClick.invoke(product, ACTION_FAVORITE, position)
                    }else{
                        onClick.invoke(product, ACTION_UNFAVORITE, position)
                    }
                }
            }
        }

        private fun calculateDiscountedPrice(originalPrice: Double, discountPercentage: Double): Double {
            val discountAmount = originalPrice * (discountPercentage / 100)
            return originalPrice - discountAmount
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Product> =
            object : DiffUtil.ItemCallback<Product>() {
                override fun areItemsTheSame(oldData: Product, newData: Product): Boolean {
                    return oldData.title == newData.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldData: Product, newData: Product): Boolean {
                    return oldData == newData
                }
            }

        const val ACTION_DETAIL = "detail"
        const val ACTION_FAVORITE = "favorite"
        const val ACTION_UNFAVORITE = "unfavorite"
    }
}