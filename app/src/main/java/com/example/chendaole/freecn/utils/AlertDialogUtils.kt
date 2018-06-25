package com.example.chendaole.freecn.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

object AlertDialogUtils {
    private var alert: AlertDialog? = null

    public fun normal(context: Context, title: String, text: String): AlertDialog {
        if (alert !== null) {
            alert!!.dismiss()
        }

        alert = AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(text)
                .setNegativeButton("确定", object: DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog!!.dismiss()
                    }
                })
                .create()
        return alert!!
    }

    public fun items(context: Context, title: String, items: Array<String>, onClickItem: DialogInterface.OnClickListener): AlertDialog {
        if (alert !== null) {
            alert!!.dismiss()
        }

        alert = AlertDialog.Builder(context)
                .setTitle(title)
                .setItems(items, onClickItem)
                .create()
        return alert!!
    }
}
