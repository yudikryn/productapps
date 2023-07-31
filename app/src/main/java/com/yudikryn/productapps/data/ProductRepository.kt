package com.yudikryn.productapps.data


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.yudikryn.productapps.data.local.db.LocalDao
import com.yudikryn.productapps.data.local.entity.FavoriteEntity
import com.yudikryn.productapps.data.remote.api.ApiService
import com.yudikryn.productapps.data.remote.model.Product
import com.yudikryn.productapps.data.remote.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository private constructor(
    private val apiService: ApiService,
    private val localDao: LocalDao
) {

    fun getListProduct(): LiveData<Result<List<Product>>> = liveData {
        emit(Result.Loading)
        try {
            withContext(Dispatchers.IO) {
                val mData = MutableLiveData<List<Product>>()
                val data: LiveData<List<Product>> = mData

                val getRemoteData = apiService.getProduct().products
                val getLocalData = localDao.getFavoriteProduct()

                if (getLocalData.isNotEmpty()){
                    getRemoteData.forEach { product ->
                        getLocalData.forEach { favorite ->
                            if (product.id == favorite.id){
                                product.isFavorite = true
                            }
                        }
                    }
                }

                withContext(Dispatchers.Main) {
                    mData.value = getRemoteData
                }

                val result: LiveData<Result<List<Product>>> = data.map {Result.Success(it) }
                emitSource(result)
            }

        } catch (e: Exception) {
            Log.d("ProductRepository", "getListProduct: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun favoriteProduct(favoriteEntity: FavoriteEntity): LiveData<Result<Long>> = liveData {
        emit(Result.Loading)
        try {
            withContext(Dispatchers.IO) {
                val mData = MutableLiveData<Long>()
                val data: LiveData<Long> = mData

                val getLocalData = localDao.favoriteProduct(favoriteEntity = favoriteEntity)

                withContext(Dispatchers.Main) {
                    mData.value = getLocalData
                }

                val result: LiveData<Result<Long>> = data.map { Result.Success(it) }
                emitSource(result)
            }

        } catch (e: Exception) {
            Log.d("PokemonRepository", "favoriteProduct: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun unfavoriteProduct(uniqId: Int): LiveData<Result<Int>> = liveData {
        emit(Result.Loading)
        try {
            withContext(Dispatchers.IO) {
                val mData = MutableLiveData<Int>()
                val data: LiveData<Int> = mData

                val getLocalData = localDao.unfavoriteProduct(uniqId)

                withContext(Dispatchers.Main) {
                    mData.value = getLocalData
                }

                val result: LiveData<Result<Int>> = data.map { Result.Success(it) }
                emitSource(result)
            }

        } catch (e: Exception) {
            Log.d("PokemonRepository", "unfavoriteProduct: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: ProductRepository? = null
        fun getInstance(
            apiService: ApiService,
            localDao: LocalDao
        ): ProductRepository =
            instance ?: synchronized(this) {
                instance ?: ProductRepository(apiService, localDao)
            }.also { instance = it }
    }
}