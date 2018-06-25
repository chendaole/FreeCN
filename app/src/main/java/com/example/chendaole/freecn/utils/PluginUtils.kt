package com.example.chendaole.freecn.utils

import android.content.Context
import android.util.Log
import dalvik.system.DexClassLoader
import java.io.File
import java.lang.reflect.Field
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

object PluginUtils {
    private val PLUGINS_APK_DIR = "pluginApkDir"
    private val PLUGINS_RELEASES_DIR = "pluginReleasesDir"

    fun getPlugin(context: Context, filename: String): Plugin {
        //TODO:插件暂时存放在 assets 目录, 迁移到 files 文件夹下面
        var isSuccess: Boolean = false
        val pluginApkPath = pluginApkDir(context).path + File.separator +  filename

        try {
            isSuccess = FileUtils.copyAssetFile(context.assets, filename, pluginApkPath)
        } catch (e: Error) { }

        return Plugin(context, isSuccess, filename)
    }

    private fun pluginApkDir(context: Context): File {
        val pluginApkDir = File(
                context.filesDir.path
                        + File.separator
                        + PLUGINS_APK_DIR
        )

        if (!pluginApkDir.exists()) {
            pluginApkDir.mkdirs()
        }

        return  pluginApkDir
    }

    private fun pluginReleasesDir(context: Context, filename: String): File {
        val pluginReleasesDir = File(
                context.filesDir.path
                        + File.separator
                        + PLUGINS_RELEASES_DIR
                        + File.separator
                        + filename
        )

        if (!pluginReleasesDir.exists()) {
            pluginReleasesDir.mkdirs()
        }

        return pluginReleasesDir
    }


    class Hook {
        private var proxyActivity: Class<*>
        private var context: Context

        constructor(context: Context, proxyActivity: Class<*>) {
            this.proxyActivity = proxyActivity
            this.context = context
        }

        public fun hookAms() {
            try {
                val ActivityManagerNativeClass: Class<*> = Class.forName("android.app.ActivityManagerNative")
                val defaultField: Field = ActivityManagerNativeClass.getDeclaredField("getDefault")
                defaultField.isAccessible = true
                val defaultValue: Any = defaultField.get(null)

                val SingletionClass = Class.forName("android.util.Singleton")
                val mIntance: Field = SingletionClass.getDeclaredField("mInstance")
                mIntance.isAccessible = true

                val iActivityManagerObject: Any = mIntance.get(defaultValue)
                val IActivityManagerIntercept: Class<*> = Class.forName("android.app.IActivityManager")

                val handler = AmsInvocationHandler(iActivityManagerObject)
                var arrayCls: Array<Class<*>> = arrayOf(IActivityManagerIntercept)
                val proxy: Any = Proxy.newProxyInstance(Thread.currentThread().contextClassLoader, arrayCls, handler)

                mIntance.set(defaultValue, proxy)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

       private class AmsInvocationHandler: InvocationHandler {
           private var iActivityManagerObject: Any

           constructor(iActivityManagerObject: Any ) {
                this.iActivityManagerObject = iActivityManagerObject
           }

           override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
               Log.d("#################", "proxy")
               return method!!.invoke(iActivityManagerObject, args)
           }
       }
    }


    class Plugin {
        private var isExist: Boolean

        public var context: Context
        public var pluginApkPath:String
        public var pluginReleasesDirPath: String

        constructor(context: Context, isExist: Boolean, filename: String) {
            this.context = context
            this.isExist = isExist

            this.pluginApkPath = pluginApkDir(context).path + File.separator + filename
            this.pluginReleasesDirPath = pluginReleasesDir(context, filename).path
        }

        fun verify(): Boolean {
            return isExist
        }

        fun instance(): DexClassLoader? {
            if (!verify()) {
                return null
            }

            return DexClassLoader(pluginApkPath, pluginReleasesDirPath, null, context.classLoader)
        }
    }
}