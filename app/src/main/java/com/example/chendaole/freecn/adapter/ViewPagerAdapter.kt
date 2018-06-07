package com.example.chendaole.freecn.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.chendaole.freecn.MainActivity
import com.example.chendaole.freecn.R

class ViewPagerAdapter: PagerAdapter {
    private var pages: ArrayList<View>

    constructor(pages: ArrayList<View>) : super() {
        this.pages = pages
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return  view == `object`
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(pages[position])
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(pages[position])
        return pages[position]
    }

    fun setListener(listener: ViewPagerInterface) {
        val btn = pages[0].findViewById<Button>(R.id.btn_switch)
        btn.setOnClickListener{
            listener.onClickJumpToSubActivity()
        }
    }

    interface ViewPagerInterface {
        fun onClickJumpToSubActivity()
    }
}