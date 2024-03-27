package com.scanner.d10r.hardware.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.scanner.d10r.hardware.db.ScannerData
import com.scanner.d10r.hardware.expandable.ScannerDataViewHolder

class ScannerDataAdapter : PagingDataAdapter<ScannerData, ScannerDataViewHolder>(object :
    DiffUtil.ItemCallback<ScannerData>() {
    override fun areItemsTheSame(oldItem: ScannerData, newItem: ScannerData): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ScannerData, newItem: ScannerData): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onBindViewHolder(holder: ScannerDataViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScannerDataViewHolder =
        ScannerDataViewHolder(parent)
}