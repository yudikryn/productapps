package com.yudikryn.productapps.ui.main

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yudikryn.productapps.R
import com.yudikryn.productapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private lateinit var gestureDetector: GestureDetector
    private val swipeSensitivity = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                event1: MotionEvent,
                event2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (event1.x < swipeSensitivity) {
                    if (event2.x - event1.x > swipeSensitivity) {
                        if (binding.viewPager.currentItem > 0) {
                            binding.viewPager.setCurrentItem(
                                binding.viewPager.currentItem - 1,
                                true
                            )
                        }
                    } else if (event1.x - event2.x > swipeSensitivity) {
                        if (binding.viewPager.currentItem < binding.viewPager.adapter?.itemCount ?: 0 - 1) {
                            binding.viewPager.setCurrentItem(
                                binding.viewPager.currentItem + 1,
                                true
                            )
                        }
                    }
                }
                return true
            }
        })

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuProduct -> binding.viewPager.currentItem = 0
                R.id.menuFavorite -> binding.viewPager.currentItem = 1
                R.id.menuChat -> binding.viewPager.currentItem = 2
            }
            true
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.bottomNavigationView.menu.findItem(R.id.menuProduct).isChecked =
                        true

                    1 -> binding.bottomNavigationView.menu.findItem(R.id.menuFavorite).isChecked =
                        true

                    2 -> binding.bottomNavigationView.menu.findItem(R.id.menuChat).isChecked = true
                }
            }
        })
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

}