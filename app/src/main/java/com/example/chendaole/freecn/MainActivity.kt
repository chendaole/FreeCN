package com.example.chendaole.freecn

import android.content.DialogInterface
import android.content.Intent
import android.content.res.AssetManager
import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout


import com.example.chendaole.freecn.adapter.ViewPagerAdapter
import com.example.chendaole.freecn.utils.AlertDialogUtils
import com.example.chendaole.freecn.utils.DexClassLoaderUtils
import com.example.chendaole.freecn.utils.FileUtils
import com.example.chendaole.freecn.utils.ToastUtils
import java.io.File
import java.lang.reflect.Method

class MainActivity : AppCompatActivity() {

    private val pages = ArrayList<View>()

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
        adapter.setListener(pagesEvent)
        pager.adapter = adapter
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
        val pager = findViewById<ViewPager>(R.id.layout_pager)
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

        pager.setCurrentItem(index, true)
    }

    private val pagesEvent = object : ViewPagerAdapter.ViewPagerInterface {
        override fun onClickJumpToSubActivity() {
         //   val intent = Intent()
          //  intent.setClass(this@MainActivity, SubFreeCNActivity::class.java)
          //  this@MainActivity.startActivity(intent)
        }

        override fun onClickLoadJar() {
            val am: AssetManager =  this@MainActivity.assets
            val fileList: Array<String>  = am.list("")
            var jarFileList: Array<String> = arrayOf()

            for (filename: String in fileList) {
                var which:Int = filename.lastIndexOf(".")
                if (which > -1 ) {
                    var fileAttr = filename.substring(which + 1).toLowerCase()
                    if (fileAttr == "jar") {
                        jarFileList +=  arrayOf(filename)
                    }
                }
            }

            AlertDialogUtils.items(this@MainActivity, "assets", jarFileList, object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    val filename = jarFileList[which]
                    val targetPath = DexClassLoaderUtils.getDexDirsPath(this@MainActivity) + File.separator + filename

                    if (!FileUtils.copyAssetFile(am, filename, targetPath)) {
                        ToastUtils.show(this@MainActivity, "文件迁移失败")
                        return
                    }

                    val intent = Intent()
                    intent.putExtra("filename", jarFileList[which])
                    intent.setClass(this@MainActivity, SubFreeCNActivity::class.java)
                    this@MainActivity.startActivity(intent)
                }
            }).show()

        }
    }
}

