package com.example.togetherpet

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.togetherpet.fragment.CalendarWeekFragment
import com.example.togetherpet.fragment.DateClickListener

class CalendarViewPagerAdapter(
    fm: FragmentActivity,
    val listener: DateClickListener
) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        Log.d("testt", "getItemCount")
        return 30
    }

    override fun createFragment(position: Int): Fragment {
        Log.d("testt", "createFragment, position = $position")
        return CalendarWeekFragment.newInstance(position, listener)
    }

    companion object {
        const val START_POSITION = 30 / 2
    }
}


