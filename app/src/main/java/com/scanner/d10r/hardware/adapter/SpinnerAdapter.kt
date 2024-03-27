package com.scanner.d10r.hardware.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class SpinnerAdapter<T>(
    context: Context,
    resource: Int,
    objects: List<T>,
    val change: (Int) -> Unit
) : ArrayAdapter<T>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        change(position)
        return super.getView(position, convertView, parent)
    }
}