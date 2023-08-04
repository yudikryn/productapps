package com.yudikryn.productapps.ui.favorite

import androidx.lifecycle.ViewModel
import com.yudikryn.productapps.data.ProductRepository
import com.yudikryn.productapps.data.local.entity.FavoriteEntity

class FavoriteViewModel(private val productRepository: ProductRepository) : ViewModel() {
    fun getMyFavoriteProduct() = productRepository.getMyFavoriteProduct()
    fun favoriteProduct(favoriteEntity: FavoriteEntity) = productRepository.favoriteProduct(favoriteEntity)
    fun unfavoriteProduct(uniqId: Int) = productRepository.unfavoriteProduct(uniqId)
}