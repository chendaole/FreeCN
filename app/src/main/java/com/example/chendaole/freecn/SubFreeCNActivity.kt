package com.example.chendaole.freecn

//import com.example.chendaole.freecn.R
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import com.example.chendaole.freecn.utils.AlertDialogUtils
import com.example.chendaole.freecn.utils.DexClassLoaderUtils
import java.lang.reflect.Method

class SubFreeCNActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_free_cn)

        initListView()
    }

    private fun initListView() {
        val filename = getIntent().getStringExtra("filename")
        val cls: Class<*>? =  DexClassLoaderUtils.getInstance(this@SubFreeCNActivity,filename,"com.chendaole.demo.HelloWorld")
        when(cls) {
            null -> {
                AlertDialogUtils.normal(this@SubFreeCNActivity, "提示", "无法实例对象, class文件不存在").show()
            }
            else -> {
                val obj: Any?  = cls.newInstance()
                val linearLayout  = findViewById<LinearLayout>(R.id.layout_methods)
                cls.declaredMethods.map {
                    method ->
                        val btn = Button(this@SubFreeCNActivity)
                        btn.text = "点击调用:" + method.name
                        linearLayout.addView(btn)
                        btn.setOnClickListener{
                            var params: String = findViewById<EditText>(R.id.editText_input_params).text.toString()

                            if (params.isEmpty()) {
                                params = "you"
                            }

                            val method: Method = cls.getMethod(method.name, String::class.java)
                            val result: Any = method.invoke(obj, params)
                            AlertDialogUtils.normal(this@SubFreeCNActivity, "提示" , result.toString()).show()
                        }
                }
            }
        }
    }
}
