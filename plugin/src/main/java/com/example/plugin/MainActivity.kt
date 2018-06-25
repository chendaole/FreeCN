package com.example.plugin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val  pluginCallMe = PluginCallMe()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    fun initView() {
        val textView = findViewById<TextView>(R.id.t_view)
        textView.text = pluginCallMe.name("chendaole")
    }
}
