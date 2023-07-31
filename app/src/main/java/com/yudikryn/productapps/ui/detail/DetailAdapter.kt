package com.yudikryn.productapps.ui.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yudikryn.productapps.R
import com.yudikryn.productapps.databinding.ItemDetailBinding

class DetailAdapter() : ListAdapter<String, DetailAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    class MyViewHolder(private val binding: ItemDetailBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(product: String) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(product)
                    .apply(
                        RequestOptions.placeholderOf(R.color.black)
                            .error(R.color.black)
                    )
                    .into(binding.ivProduct)

            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<String> =
            object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(oldData: String, newData: String): Boolean {
                    return oldData == newData
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldData: String, newData: String): Boolean {
                    return oldData == newData
                }
            }
    }
}