package com.tavanhieu.chatapp.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    //số menu hiện có:
    override fun getItemCount(): Int {
        return 3
    }
    override fun createFragment(position: Int): Fragment {
        //Trả về Fragment tương ứng với vị trí:
        return when (position) {
            0 -> FragmentHomeChat()
            1 -> FragmentContactChat()
            2 -> FragmentSettingChat()
            else -> FragmentHomeChat()
        }
    }
}