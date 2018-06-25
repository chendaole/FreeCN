package com.example.chendaole.freecn.utils

import android.content.Context
import dalvik.system.DexClassLoader
import java.io.File
import java.lang.Exception

object DexClassLoaderUtils {
    val JAR_LIBS_PATH: String     = "jarDirs"
    val JAR_OUT_LIBS_PATH: String = "jarOutDirs"

    fun getDexDirsPath(content: Context): String {
        val file: File = File(content.cacheDir.path + File.separator + JAR_LIBS_PATH)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.path
    }

    fun getJarOutDirsPath(content: Context): String {
        val file: File = File(content.cacheDir.path + File.separator +JAR_OUT_LIBS_PATH)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.path
    }


    private fun dexLoader (content: Context, dexName: String) : DexClassLoader {
        val jarPath = getDexDirsPath(content) + File.separator + dexName
        return DexClassLoader(jarPath, getJarOutDirsPath(content), null, content.classLoader)
    }

    fun getInstance(content: Context, dexName: String, packageName: String): Class<*>? {
        val loader: DexClassLoader = dexLoader(content, dexName)
        var obj: Class<*>
        try {
            obj =  loader.loadClass(packageName)
        } catch (e: Exception) {
            return  null
        }
        return obj
    }
}
