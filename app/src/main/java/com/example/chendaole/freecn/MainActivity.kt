package com.example.chendaole.freecn


import android.app.Fragment
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.DialogInterface
import android.content.Intent
import android.content.res.AssetManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.*
import java.io.File

import com.example.chendaole.freecn.utils.AlertDialogUtils
import com.example.chendaole.freecn.utils.DexClassLoaderUtils
import com.example.chendaole.freecn.utils.FileUtils
import com.example.chendaole.freecn.utils.ToastUtils
import com.example.chendaole.freecn.view.fragment.AnalyzeFragment
import com.example.chendaole.freecn.view.fragment.ApplicationFragment
import com.example.chendaole.freecn.view.fragment.ModuleFragment

class MainActivity : AppCompatActivity(),
        ApplicationFragment.OnFragmentInteractionListener,
        ModuleFragment.OnFragmentInteractionListener,
        AnalyzeFragment.OnFragmentInteractionListener {
    private lateinit var fragments: Array<Fragment>
    private lateinit var manager: FragmentManager
    // private val pages = ArrayList<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        initView()

        manager = fragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.add(R.id.main_fragment, fragments[0])
        transaction.commit()

        initEvent()
    }

    /**
     *  处理viewPager初始化
     *
     */
    private fun initView() {
        fragments = arrayOf(ApplicationFragment(), ModuleFragment(), AnalyzeFragment())
        val tab_application = findViewById<LinearLayout>(R.id.tab_application)
        tab_application.getChildAt(0).isSelected = true
        tab_application.getChildAt(1).isSelected = true
    }

    /**
     * fragment 页面切换
     * **/

    private fun fragmentSwitch(index: Int) {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        val fragment: Fragment = fragments[index]



        transaction.replace(R.id.main_fragment, fragment)
        transaction.commit()
    }

    /**
     * 处理事件监听
     *
     * */
    private fun initEvent() {
        val tabApplication = findViewById<LinearLayout>(R.id.tab_application)
        val tabModule = findViewById<LinearLayout>(R.id.tab_module)
        val tabAnalyze = findViewById<LinearLayout>(R.id.tab_analyze)

        tabApplication.setOnClickListener{
            dispatchTabsEvent(it.id)
        }

        tabModule.setOnClickListener{
            dispatchTabsEvent(it.id)
        }

        tabAnalyze.setOnClickListener{
            dispatchTabsEvent(it.id)
        }
    }

    private fun dispatchTabsEvent(id: Int) {
      //  val pager = findViewById<ViewPager>(R.id.layout_pager)
        val index: Int = when(id) {
            R.id.tab_application ->  0
            R.id.tab_module -> 1
            R.id.tab_analyze -> 2
            else -> 0
        }

        val tabs: LinearLayout = findViewById<LinearLayout>(R.id.layout_tabs)

        for (i in 0..(tabs.childCount -1)) {
            val layoutTab = tabs.getChildAt(i) as LinearLayout
            val imageButton = layoutTab.getChildAt(0)
            val textView = layoutTab.getChildAt(1)

            textView.isSelected = i == index
            imageButton.isSelected = i == index
        }

        fragmentSwitch(index)
      //  pager.setCurrentItem(index, true)
    }


    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

