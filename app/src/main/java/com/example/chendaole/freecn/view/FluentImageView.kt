package com.example.chendaole.freecn.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.ImageView

import com.example.chendaole.freecn.R

class FluentImageView: ImageView {

    private val MODE_NONE   : Int = 0
    private val MODE_CIRCLE : Int = 1
    private val MODE_ROUND  : Int = 2

    private var curr_mode : Int = 1

    constructor(context : Context) : super(context){

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        val type : TypedArray = context.obtainStyledAttributes(attrs, R.styleable.FluentImageView, defStyleAttr, 0)
        if (type.hasValue(R.styleable.FluentImageView_type)) {
            curr_mode = type.getInt(R.styleable.FluentImageView_type, MODE_NONE)
        } else {
            curr_mode = MODE_NONE
        }

    }

    protected override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

    }
}