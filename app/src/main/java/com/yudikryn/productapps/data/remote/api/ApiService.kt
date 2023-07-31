package com.yudikryn.productapps.data.remote.api

import com.yudikryn.productapps.data.remote.model.ProductResponse
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProduct(): ProductResponse
}