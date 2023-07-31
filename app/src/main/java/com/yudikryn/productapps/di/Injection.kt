package com.yudikryn.productapps.di

import android.content.Context
import com.yudikryn.productapps.data.ProductRepository
import com.yudikryn.productapps.data.local.db.LocalDb
import com.yudikryn.productapps.data.remote.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): ProductRepository {
        val apiService = ApiConfig.getApiService()
        val localDao = LocalDb.getInstance(context).localDao()
        return ProductRepository.getInstance(apiService, localDao)
    }
}