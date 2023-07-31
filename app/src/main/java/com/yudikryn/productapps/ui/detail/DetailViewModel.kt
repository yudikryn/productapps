package com.yudikryn.productapps.ui.detail

import androidx.lifecycle.ViewModel
import com.yudikryn.productapps.data.ProductRepository
import com.yudikryn.productapps.data.local.entity.FavoriteEntity

class DetailViewModel(private val productRepository: ProductRepository) : ViewModel() {
    fun favoriteProduct(favoriteEntity: FavoriteEntity) = productRepository.favoriteProduct(favoriteEntity)
    fun unfavoriteProduct(uniqId: Int) = productRepository.unfavoriteProduct(uniqId)
}