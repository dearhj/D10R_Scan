package com.scanner.hardware.expandable

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.scanner.hardware.R
import com.scanner.hardware.db.ScannerData

class ScannerDataViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_scanner_data, parent, false)
) {
    private val id = itemView.findViewById<TextView>(R.id.id)
    private val data = itemView.findViewById<TextView>(R.id.data)
    private var scannerdata: ScannerData? = null
    fun bindTo(data1: ScannerData?) {
        scannerdata = data1
        id.text = data1?.id.toString()
        data.text = data1?.data
    }
}