package com.yudikryn.productapps.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yudikryn.productapps.R
import com.yudikryn.productapps.data.local.entity.FavoriteEntity
import com.yudikryn.productapps.data.remote.model.Product
import com.yudikryn.productapps.databinding.ActivityMainBinding
import com.yudikryn.productapps.helper.ViewModelFactory
import com.yudikryn.productapps.data.remote.network.Result
import com.yudikryn.productapps.ui.detail.DetailActivity
import com.yudikryn.productapps.ui.detail.DetailActivity.Companion.PRODUCT_EXTRAS
import com.yudikryn.productapps.ui.main.MainAdapter.Companion.ACTION_DETAIL
import com.yudikryn.productapps.ui.main.MainAdapter.Companion.ACTION_FAVORITE
import com.yudikryn.productapps.ui.main.MainAdapter.Companion.ACTION_UNFAVORITE

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var viewModel: MainViewModel

    private val mainAdapter by lazy {
        MainAdapter{ product, action, position ->
            when(action){
                ACTION_DETAIL -> {
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra(PRODUCT_EXTRAS, product)
                    startActivity(intent)
                }
                ACTION_FAVORITE -> {
                   favoriteProduct(product, position)
                }
                ACTION_UNFAVORITE -> {
                    unfavoriteProduct(product, position)
                }
            }
        }
    }

    private fun unfavoriteProduct(product: Product,position: Int){
        viewModel.unfavoriteProduct(product.id).observe(this){result ->
            when(result){
                is Result.Success ->{
                    val products = Product(
                        id = product.id,
                        title = product.title,
                        description = product.description,
                        price = product.price,
                        discountPercentage = product.discountPercentage,
                        rating = product.rating,
                        stock = product.stock,
                        brand = product.brand,
                        category = product.category,
                        thumbnail = product.thumbnail,
                        images = product.images,
                        isFavorite = !product.isFavorite,
                    )
                    mainAdapter.updateItem(products, position)
                    Toast.makeText(this, "${product.title} delete from favorite", Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun favoriteProduct(product: Product, position: Int){
        viewModel.favoriteProduct(FavoriteEntity(id = product.id)).observe(this){result ->
            when(result){
                is Result.Success ->{
                    val products = Product(
                        id = product.id,
                        title = product.title,
                        description = product.description,
                        price = product.price,
                        discountPercentage = product.discountPercentage,
                        rating = product.rating,
                        stock = product.stock,
                        brand = product.brand,
                        category = product.category,
                        thumbnail = product.thumbnail,
                        images = product.images,
                        isFavorite = !product.isFavorite,
                    )
                    mainAdapter.updateItem(products, position)
                    Toast.makeText(this, "${product.title} add to favorite", Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        val viewModels: MainViewModel by viewModels {
            factory
        }
        viewModel = viewModels
        viewModels.getListProduct().observe(this){result ->
            when(result){
                is Result.Success ->{
                    if (result.data.isNotEmpty()){
                        mainAdapter.submitList(result.data)
                        binding.rvProduct.apply {
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            setHasFixedSize(true)
                            adapter = mainAdapter
                        }
                    }
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}