package com.example.chendaole.freecn.view

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Environment
import android.provider.MediaStore
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import com.example.chendaole.freecn.utils.ImageLoader
import java.io.File

class FluentScrollView : ScrollView {
    private var curr_page : Int = 0
    private val list : List<ImageView> = ArrayList<ImageView>()

    private lateinit var columnWidth : Int
    private lateinit var firstColumnHeight  : Int
    private lateinit var secondColumnHeight : Int
    private lateinit var imageCache : ImageLoader
    private lateinit var firstColumn: LinearLayout
    private lateinit var secondColumn : LinearLayout

    constructor(context: Context) : super(context)

    constructor(context: Context , attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes)

    companion object {
        private val MAX_PAGE_SIZE : Int = 25
        private val collectionTasks: Set<>
    }

    inner class LoaderImageTask: AsyncTask<String, Unit, Bitmap> {
        private lateinit var mImageUrl: String
        private lateinit var mImageView: ImageView

        constructor(): super()
        constructor(imageView: ImageView) {
            mImageView = imageView
        }

        override fun doInBackground(vararg params: String?): Bitmap {
            mImageUrl = params[0] as String
            val imageBitmap: Bitmap? = this@FluentScrollView.imageCache.getBitmapFromMemoryCache(mImageUrl)
            if (imageBitmap == null) {

            }

            return imageBitmap!!
        }

        private fun loadeImage(imageUrl: String) {
            val imageFile = File(this.getImagePath(imageUrl))
            if (!imageFile.exists()) {

            }
        }

        private fun downloadImage(imageUrl: String): Bitmap {

        }

        private fun getImagePath(imageUrl: String): String {
            val lastSlashIndex: Int = imageUrl.lastIndexOf("/")
            val imageName: String = imageUrl.substring(lastSlashIndex + 1)
            val imageDir = Environment.getExternalStorageDirectory().path + "/PhotoWallFalls/";
            val file: File = File(imageDir)

            if (!file.exists()) {
                file.mkdirs()
            }

            return  imageDir + imageName
        }

    }
}