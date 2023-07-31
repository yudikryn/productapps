package com.yudikryn.productapps.ui.main

import androidx.lifecycle.ViewModel
import com.yudikryn.productapps.data.ProductRepository
import com.yudikryn.productapps.data.local.entity.FavoriteEntity

class MainViewModel(private val productRepository: ProductRepository) : ViewModel() {
    fun getListProduct() = productRepository.getListProduct()
    fun favoriteProduct(favoriteEntity: FavoriteEntity) = productRepository.favoriteProduct(favoriteEntity)
    fun unfavoriteProduct(uniqId: Int) = productRepository.unfavoriteProduct(uniqId)
}