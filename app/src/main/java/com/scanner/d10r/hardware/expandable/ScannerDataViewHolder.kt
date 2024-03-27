package com.scanner.d10r.hardware.expandable

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.scanner.d10r.hardware.db.ScannerData
import com.scannerd.d10r.hardware.R

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