package com.dnc.kt.quantumassignment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.flyco.tablayout.SegmentTabLayout
import com.flyco.tablayout.listener.OnTabSelectListener

class loginActivity : AppCompatActivity() {
    lateinit var viewpager: ViewPager
    lateinit var tablayout: SegmentTabLayout
    private val mTitles = arrayOf("Sign Up","Sign In")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)




        viewpager = findViewById(R.id.viewpager)
        tablayout = findViewById(R.id.tablayout)


        tablayout.setTabData(mTitles)
        tablayout.setTabSpaceEqual(true)
        //blue color #2C97DE

        val adapter = TabLayoutAdapter(
            this,
            supportFragmentManager, tablayout.getTabCount()
        )

        viewpager.setAdapter(adapter)

        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                Log.d("page selected", "" + position)
                tablayout.setCurrentTab(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        tablayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                Log.d("TAB", "" + position)
                viewpager.setCurrentItem(position)
            }

            override fun onTabReselect(position: Int) {}
        })

    }

    class TabLayoutAdapter(context: Context, fragmentManager: FragmentManager?, totalTabs: Int) :
        FragmentPagerAdapter(fragmentManager!!) {
        var mContext: Context
        var mTotalTabs: Int
        override fun getItem(position: Int): Fragment {
            Log.d("asasas", position.toString() + "")
            return when (position) {
                0 -> signupFragment()
                1 -> signInFragment()
                else -> signInFragment()
            }
        }

        override fun getCount(): Int {
            return mTotalTabs
        }

        init {
            mContext = context
            mTotalTabs = totalTabs
        }
    }

}