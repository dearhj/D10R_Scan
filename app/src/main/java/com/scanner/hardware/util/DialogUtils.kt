package com.scanner.hardware.util

import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.scanner.hardware.R
import com.scanner.hardware.ui.PublicTipsDialog

fun choseSingleDialog(
    context: Context,
    title: String = "",
    data: Array<String> = emptyArray(),
    select: Int,
    ok: ((DialogInterface, Int) -> Unit) = { _, _ -> }
) {
    try {
        var pos = select
        val alertBuilder = AlertDialog.Builder(context)
        alertBuilder.setTitle(title)
        alertBuilder.setSingleChoiceItems(data, select) { _, i: Int -> pos = i }
        alertBuilder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            ok(dialog, pos)
        }
        alertBuilder.setNegativeButton(android.R.string.cancel, null)
        val alertDialog = alertBuilder.create()
        alertDialog.show()
    } catch (_: Exception){
    }
}

fun imageDialog(
    context: Context, title: String = "", bitmap: Bitmap, listener: (() -> Unit) = {}
) {
    val alertBuilder = AlertDialog.Builder(context)
    alertBuilder.setIcon(R.mipmap.icon_tm)
    alertBuilder.setTitle(title)
    val v = View.inflate(context, R.layout.dialog_iv, null)
    val iv = v.findViewById<ImageView>(com.scanner.hardware.R.id.iv)
    iv.setImageBitmap(bitmap)
    alertBuilder.setView(v)
    alertBuilder.setPositiveButton(android.R.string.ok) { _, _ -> listener() }
    alertBuilder.setNegativeButton(android.R.string.cancel, null)
    alertBuilder.create().show()
}

fun editDialog(context: Context, view: View, title: String = "", listener: (() -> Unit) = {}) {
    val alertBuilder = AlertDialog.Builder(context)
    alertBuilder.setTitle(title)
    alertBuilder.setView(view)
    alertBuilder.setPositiveButton(android.R.string.ok) { _, _ -> listener() }
    alertBuilder.setNegativeButton(android.R.string.cancel, null)
    alertBuilder.create().show()
}

fun confirmDialogMessage(context: Context, message: String = "", listener: (() -> Unit) = {}) {
    val alertBuilder = AlertDialog.Builder(context)
    alertBuilder.setIcon(R.mipmap.icon_tm)
    alertBuilder.setTitle(context.getString(com.scanner.hardware.R.string.app_name))
    alertBuilder.setMessage(message)
    alertBuilder.setPositiveButton(android.R.string.ok) { _, _ -> listener() }
    alertBuilder.setNegativeButton(android.R.string.cancel, null)
    alertBuilder.create().show()
}

fun confirmDialog(context: Context, title: String? = "", listener: (() -> Unit) = {}) {
    val alertBuilder = AlertDialog.Builder(context)
    alertBuilder.setIcon(R.mipmap.icon_tm)
    alertBuilder.setTitle(title)
    alertBuilder.setPositiveButton(android.R.string.ok) { _, _ -> listener() }
    alertBuilder.setNegativeButton(android.R.string.cancel, null)
    alertBuilder.create().show()
}

fun showDialog(
    context: Context,
    title: String = "",
    key1: String,
    value1: String,
    key2: String,
    value2: String,
    listener: ((DialogInterface?, Int, View) -> Unit) = { _, _, _ -> }
) {
    val alertBuilder = AlertDialog.Builder(context)
    alertBuilder.setTitle(title)
    val v = View.inflate(context, R.layout.dialog_two_key, null)
    alertBuilder.setView(v)
    val tvKey1 = v.findViewById<TextView>(R.id.tv_key1)
    tvKey1.text = key1
    val tvKey2 = v.findViewById<TextView>(R.id.tv_key2)
    tvKey2.text = key2
    val etValue1 = v.findViewById<EditText>(R.id.et_value1)
    etValue1.hint = key1
    etValue1.setText(value1)
    val etValue2 = v.findViewById<EditText>(R.id.et_value2)
    etValue2.hint = key2
    etValue2.setText(value2)
    alertBuilder.setPositiveButton(android.R.string.ok) { dialog: DialogInterface?, which: Int ->
        listener(dialog, which, v)
    }
    alertBuilder.setNegativeButton(android.R.string.cancel, null)
    alertBuilder.create().show()
}

fun showPublicTipsDialog(
    context: AppCompatActivity, title: String? = "", sureOnClick: (() -> Unit) = {},
) {
    val dialog = PublicTipsDialog(title = title, sureOnClick = sureOnClick)
    dialog.show(context.supportFragmentManager, "dd")
}
