package com.example.chendaole.freecn.view.component

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView

import com.example.chendaole.freecn.R

class FluentImageView: ImageView {

    private val MODE_NONE   : Int = 0
    private val MODE_CIRCLE : Int = 1
    private val MODE_ROUND  : Int = 2

    private var currMode : Int = 1
    private var currRound : Int = dp2px(10f)

    private val mPaint :Paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)

    constructor(context : Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        val type : TypedArray = context.obtainStyledAttributes(attrs, R.styleable.FluentImageView, defStyleAttr, 0)

        currMode = if (type.hasValue(R.styleable.FluentImageView_type)) {
            type.getInt(R.styleable.FluentImageView_type, MODE_NONE)
        } else {
            MODE_NONE
        }

        currRound = if (type.hasValue(R.styleable.FluentImageView_radius)) {
            type.getDimensionPixelSize(R.styleable.FluentImageView_radius, currRound)
        } else {
            currRound
        }
        type.recycle()
    }

    private fun dp2px(value : Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, resources.displayMetrics).toInt()
    }

    private fun drawable2Bitmap(drawable : Drawable?) : Bitmap? {
        if (drawable == null) {
            return null
        }

        val bitmap : Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val matrix : Matrix? = imageMatrix

        if (matrix !== null) {
            canvas.concat(matrix)
        }

        drawable.draw(canvas)
        return  bitmap
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (currMode == MODE_CIRCLE) {
            val result : Int = Math.min(widthMeasureSpec, heightMeasureSpec)
            setMeasuredDimension(result, result)
        }
    }

    override fun onDraw(canvas: Canvas) {
       // super.onDraw(canvas)
        val mDrawable = drawable
        val mDrawMatrix = imageMatrix

        if (mDrawable == null) {
            return  // couldn't resolve the URI
        }

        if (mDrawable.intrinsicWidth == 0 || mDrawable.intrinsicHeight == 0) {
            return      // nothing to draw (empty bounds)
        }

        if (mDrawMatrix == null && paddingTop == 0 && paddingLeft == 0) {
            mDrawable.draw(canvas)
        } else {
            val saveCount = canvas.getSaveCount()
            canvas.save()

            if (cropToPadding) {
                val scrollX = scrollX
                val scrollY = scrollY
                canvas.clipRect(scrollX + paddingLeft, scrollY + paddingTop,
                        scrollX + right - left - paddingRight,
                        scrollY + bottom - top - paddingBottom)
            }

            canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())

            when(currMode) {
                MODE_CIRCLE -> {
                    val bitmap : Bitmap? = drawable2Bitmap(mDrawable)
                    mPaint.shader = object : BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP){}
                    canvas.drawCircle((width / 2).toFloat() , (height / 2).toFloat(), (width / 2).toFloat(), mPaint)
                }

                MODE_ROUND -> {
                    val bitmap : Bitmap? = drawable2Bitmap(mDrawable)
                    mPaint.shader = object : BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP){}
                    val rect = RectF(0f, 0f ,  width.toFloat() , y + height.toFloat())
                    canvas.drawRoundRect(rect, currRound.toFloat(), currRound.toFloat(), mPaint)
                }

                else -> {
                    if (mDrawMatrix != null) {
                        canvas.concat(mDrawMatrix)
                    }
                    mDrawable.draw(canvas)
                }
            }

            canvas.restoreToCount(saveCount)
        }
    }
}