package com.example.chendaole.freecn

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import android.view.Window
import android.widget.Button


import com.example.chendaole.freecn.adapter.ViewPagerAdapter

class MainActivity : AppCompatActivity() {

    val pages = ArrayList<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        initViewPager()

        initEvent()

    }

    /**
     *  处理viewPager初始化
     *
     */
    private fun initViewPager() {
        val pager = findViewById<ViewPager>(R.id.layout_pager)
        pages.add(layoutInflater.inflate(R.layout.layout_page_home, null))
        pages.add(layoutInflater.inflate(R.layout.layout_page_custom, null))
        pages.add(layoutInflater.inflate(R.layout.layout_page_user, null))

        val adapter = ViewPagerAdapter(pages)
        adapter.setListener(object : ViewPagerAdapter.ViewPagerInterface {
            override fun onClickJumpToSubActivity() {
                var intent = Intent()
                intent.setClass(this@MainActivity, SubFreeCNActivity::class.java)
                this@MainActivity.startActivity(intent)
            }
        })
        pager.adapter = adapter
    }

    /**
     * 处理事件监听
     *
     * */
    private fun initEvent() {

        val pager = findViewById<ViewPager>(R.id.layout_pager)
        val btnHome = findViewById<Button>(R.id.btn_home)
        btnHome.setOnClickListener {
            pager.setCurrentItem(0, true)
        }

        val btnCustom = findViewById<Button>(R.id.btn_custom)
        btnCustom.setOnClickListener {
            pager.setCurrentItem(1, true)
        }

        val btnUser = findViewById<Button>(R.id.btn_user)
        btnUser.setOnClickListener {
            pager.setCurrentItem(2, true)
        }
    }
}

