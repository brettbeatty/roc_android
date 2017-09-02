package com.brettbeatty.roc.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.brettbeatty.roc.R
import com.brettbeatty.roc.fragments.PassListFragment
import com.brettbeatty.roc.fragments.ScheduleFragment
import com.brettbeatty.roc.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {
    val pager: ViewPager by lazy { findViewById<ViewPager>(R.id.container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pager.adapter = MainAdapter(supportFragmentManager)
        pager.currentItem = SCHEDULE
    }

    class MainAdapter(manager: FragmentManager): FragmentPagerAdapter(manager) {
        val passListFragment = PassListFragment.newInstance()
        val scheduleFragment = ScheduleFragment.newInstance()
        val settingsFragment = SettingsFragment.newInstance()

        override fun getCount(): Int = 3

        override fun getItem(position: Int): Fragment {
            return when (position) {
                PASSES -> passListFragment
                SCHEDULE -> scheduleFragment
                SETTINGS -> settingsFragment
                else -> scheduleFragment
            }
        }
    }

    companion object {
        val PASSES = 0
        val SCHEDULE = 1
        val SETTINGS = 2
        val TAG: String = MainActivity::class.java.simpleName
    }
}
