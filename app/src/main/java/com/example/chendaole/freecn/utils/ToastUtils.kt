package com.example.chendaole.freecn.utils

import android.content.Context
import android.widget.Toast

object ToastUtils {
    private  var toast: Toast? = null

    public fun show(context: Context, text: String) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        } else {
            toast!!.setText(text)
        }
        toast!!.show()
    }
}