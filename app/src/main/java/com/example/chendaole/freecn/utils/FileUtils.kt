package com.example.chendaole.freecn.utils

import android.content.res.AssetManager
import android.util.Log
import java.io.*
import java.nio.channels.FileChannel

object FileUtils {
    fun copyFile(source: File, target: String?): Boolean {
        var result = false
        if (source == null || target == null) {
            return result
        }
        val dest = File(target)
        if (dest != null && dest.exists()) {
            dest.delete() // delete file
        }
        try {
            dest.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        var srcChannel: FileChannel? = null
        var dstChannel: FileChannel? = null

        try {
            srcChannel = FileInputStream(source).getChannel()
            dstChannel = FileOutputStream(dest).getChannel()
            srcChannel!!.transferTo(0, srcChannel!!.size(), dstChannel)
            result = true
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return result
        } catch (e: IOException) {
            e.printStackTrace()
            return result
        }

        try {
            srcChannel!!.close()
            dstChannel!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return result
    }

    fun copyAssetFile(am: AssetManager, source: String, target: String): Boolean {

        try {
            val outFile: File = File(target)
            if (outFile.exists()) {
                outFile.delete()
            }

            val inputStream:  InputStream = am.open(source)
            val outStream: FileOutputStream = FileOutputStream(target)
            val buffer: ByteArray = ByteArray(1024)
            var byteCount: Int

            byteCount = inputStream.read(buffer)
            while ( byteCount != -1) {
                outStream.write(buffer, 0 , byteCount)
                byteCount = inputStream.read(buffer)
            }

            outStream.flush()
            inputStream.close()
            outStream.close()

            return  true
        } catch (e: IOException) {
            return false
        }
    }
}
