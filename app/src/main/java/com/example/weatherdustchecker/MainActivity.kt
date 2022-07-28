package com.example.weatherdustchecker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.Exception
import java.lang.reflect.Array.newInstance
import javax.xml.datatype.DatatypeFactory.newInstance

class MainActivity : AppCompatActivity() {
    private lateinit var mPager: ViewPager
    private var lat :Double = 37.579876
    private var lon :Double = 126.976998

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //상단 제목 표시줄 숨기기
        supportActionBar?.hide()

        mPager = findViewById(R.id.pager)
        val pagerAdapter = MyPagerAdpter(supportFragmentManager, lat, lon)
        mPager.adapter = pagerAdapter

        mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                if(position == 0){
                    Toast.makeText(applicationContext,
                    "날씨 페이지입니다",
                    Toast.LENGTH_SHORT).show()
                }
                else if(position == 1){
                        Toast.makeText(applicationContext,
                            "미세먼지 페이지입니다",
                            Toast.LENGTH_SHORT).show()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}

        })

    }
    class MyPagerAdpter(fm:FragmentManager, val lat:Double, val lon:Double) : FragmentStatePagerAdapter(fm){
        override fun getCount()=2

        override fun getItem(position: Int): Fragment {
            return when(position){
                0 -> WeatherPageFragment.newInstance(lat, lon)
                1 -> DustPageFragment.newInstance(lat, lon)
                else -> throw Exception("페이지가 존재하지 않음")
            }
        }

    }
}