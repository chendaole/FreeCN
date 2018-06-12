package com.example.chendaole.freecn.utils

import android.graphics.Bitmap
import android.util.LruCache
import kotlin.system.measureTimeMillis

class ImageLoader {
    private var  mMemoryCache : LruCache<String, Bitmap>

    constructor() {
        val maxMemory : Long = Runtime.getRuntime().maxMemory()
        val currSize : Int = (maxMemory / 8).toInt()
        mMemoryCache = object : LruCache<String, Bitmap>(currSize) {
            override fun sizeOf(key: String?, value: Bitmap?): Int {
                var size : Int  = 0
                size = if (value == null) {
                    size
                } else {
                    value.byteCount
                }
                return size
            }
        }
    }

    public fun getBitmapFromMemoryCache(key : String) : Bitmap? {
        return mMemoryCache.get(key)
    }

    public fun setBitmapToMemoryCache(key: String, bitmap: Bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap)
        }
    }

    companion object {
        private var mImageLoader : ImageLoader? = null

        fun GetInstance() : ImageLoader? {
           if (mImageLoader == null) {
               mImageLoader = ImageLoader()
           }
            return mImageLoader
       }
   }
}