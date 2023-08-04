package com.yudikryn.productapps.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yudikryn.productapps.ui.chat.ChatFragment
import com.yudikryn.productapps.ui.favorite.FavoriteFragment
import com.yudikryn.productapps.ui.product.ProductFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProductFragment()
            1 -> FavoriteFragment()
            else -> ChatFragment()
        }
    }
}