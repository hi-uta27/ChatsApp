package com.tavanhieu.chatapp.activity

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tavanhieu.chatapp.R
import com.tavanhieu.chatapp.fragment_trang_chu.FragmentContactChat
import com.tavanhieu.chatapp.fragment_trang_chu.FragmentHomeChat
import com.tavanhieu.chatapp.fragment_trang_chu.FragmentSettingChat

class MainActivity : UserActiveActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.FragmentLayoutMain, FragmentHomeChat()).commit()
        bottomNavigation = findViewById(R.id.bottomNavigationMain)
        mOnClick()
    }

    private fun mOnClick() {
        bottomNavigation.setOnItemSelectedListener { item ->
            val supportFragment = supportFragmentManager.beginTransaction()
            when (item.itemId) {
                R.id.menu_home -> {
                    supportFragment.replace(R.id.FragmentLayoutMain, FragmentHomeChat()).commit()
                    true
                }
                R.id.menu_contact -> {
                    supportFragment.replace(R.id.FragmentLayoutMain, FragmentContactChat()).commit()
                    true
                }
                R.id.menu_Setting -> {
                    supportFragment.replace(R.id.FragmentLayoutMain, FragmentSettingChat()).commit()
                    true
                }
                else -> false
            }
        }
    }

//    private fun setViewPager() {
////        Khi vuốt chuyển layout sẽ chọn --menu-- theo layout tương ứng:
//        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                when(position) {
//                    //Khi page chuyển đến layout fragment nào thì menu sẽ chuyển theo tương ứng:
//                    0 -> bottomNavigation.menu.findItem(R.id.menu_home).isChecked    = true
//                    1 -> bottomNavigation.menu.findItem(R.id.menu_contact).isChecked = true
//                    2 -> bottomNavigation.menu.findItem(R.id.menu_Setting).isChecked = true
//                    else -> bottomNavigation.menu.findItem(R.id.menu_home).isChecked = true
//                }
//            }
//        })
//    }
}