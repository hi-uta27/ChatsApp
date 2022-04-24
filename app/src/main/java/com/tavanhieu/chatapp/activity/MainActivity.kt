package com.tavanhieu.chatapp.activity

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tavanhieu.chatapp.R
import com.tavanhieu.chatapp.view_pager.ViewPagerAdapter

class MainActivity : UserActiveActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottomNavigationMain)
        viewPager2 = findViewById(R.id.ViewPagerLayoutMain)

        setViewPager()
        //Ánh xạ màn hình cho viewpager(List Fragment Layout):
        viewPager2.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        mOnClick()
    }

    private fun setViewPager() {
//        Khi vuốt chuyển layout sẽ chọn --menu-- theo layout tương ứng:
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when(position) {
                    //Khi page chuyển đến layout fragment nào thì menu sẽ chuyển theo tương ứng:
                    0 -> bottomNavigation.menu.findItem(R.id.menu_home).isChecked    = true
                    1 -> bottomNavigation.menu.findItem(R.id.menu_contact).isChecked = true
                    2 -> bottomNavigation.menu.findItem(R.id.menu_Setting).isChecked = true
                    else -> bottomNavigation.menu.findItem(R.id.menu_home).isChecked = true
                }
            }
        })
    }

    private fun mOnClick() {
        bottomNavigation.setOnItemSelectedListener { item ->
            //item.itemId: Lấy ra id của menu được chọn:
            //Khi click vào menu nào thì sẽ hiển thị fragment của layout đó:
            when (item.itemId) {
                R.id.menu_home -> {
                    viewPager2.currentItem = 0
                    true
                }
                R.id.menu_contact -> {
                    //set menu item được chọn là màn hình Fragment nào:
                    viewPager2.currentItem = 1
                    true
                }
                R.id.menu_Setting -> {
                    viewPager2.currentItem = 2
                    true
                }
                else -> false
            }
        }
    }
}