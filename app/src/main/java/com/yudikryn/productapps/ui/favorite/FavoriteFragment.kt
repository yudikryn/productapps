package com.yudikryn.productapps.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yudikryn.productapps.data.local.entity.FavoriteEntity
import com.yudikryn.productapps.data.remote.model.Product
import com.yudikryn.productapps.data.remote.network.Result
import com.yudikryn.productapps.databinding.FragmentFavoriteBinding
import com.yudikryn.productapps.helper.ViewModelFactory
import com.yudikryn.productapps.ui.detail.DetailActivity

class FavoriteFragment : Fragment() {

    private lateinit var viewModel: FavoriteViewModel

    private lateinit var binding: FragmentFavoriteBinding

    private val favoriteAdapter by lazy {
        FavoriteAdapter{ product, action, position ->
            when(action){
                FavoriteAdapter.ACTION_DETAIL -> {
                    val intent = Intent(requireContext(), DetailActivity::class.java)
                    intent.putExtra(DetailActivity.PRODUCT_EXTRAS, product)
                    context?.startActivity(intent)
                }
                FavoriteAdapter.ACTION_FAVORITE -> {}
                FavoriteAdapter.ACTION_UNFAVORITE -> {
                    unfavoriteProduct(product, position)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModels: FavoriteViewModel by viewModels {
            factory
        }
        viewModel = viewModels
    }

    private fun getData(){
        viewModel.getMyFavoriteProduct().observe(viewLifecycleOwner){result ->
            when(result){
                is Result.Success ->{
                    if (result.data.isNotEmpty()){
                        favoriteAdapter.submitList(result.data)
                        binding.rvFavorite.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            setHasFixedSize(true)
                            adapter = favoriteAdapter
                        }
                    }
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun unfavoriteProduct(product: Product, position: Int){
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
                    favoriteAdapter.updateItem(products, position)
                    Toast.makeText(requireContext(), "${product.title} delete from favorite", Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun favoriteProduct(product: Product, position: Int){
        viewModel.favoriteProduct(FavoriteEntity(id = product.id)).observe(this){ result ->
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
                    favoriteAdapter.updateItem(products, position)
                    Toast.makeText(requireContext(), "${product.title} add to favorite", Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }
}