package com.scanner.d10r.hardware

import android.app.ProgressDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.h4de5ing.base.append
import com.scanner.d10r.hardware.adapter.ScannerDataAdapter
import com.scanner.d10r.hardware.base.BaseBackActivity
import com.scanner.d10r.hardware.expandable.LoadMoreViewModel
import com.scannerd.d10r.hardware.R
import com.scannerd.d10r.hardware.databinding.ActivityRecyclerviewBinding
import java.io.File

class DataActivity : BaseBackActivity() {
    private lateinit var viewModel: com.scanner.d10r.hardware.expandable.LoadMoreViewModel
    private lateinit var normalDialog: AlertDialog.Builder
    private lateinit var binding: ActivityRecyclerviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[com.scanner.d10r.hardware.expandable.LoadMoreViewModel::class.java]
        MyApplication.dao.observeDataChange().observe(this) {
            title = "${getString(R.string.scanner_result)}(${MyApplication.dao.selectData()})"
        }
        val layoutManager = LinearLayoutManager(this)
        val adapter = ScannerDataAdapter()
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        viewModel.paging.observe(this) { adapter.submitData(lifecycle, it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_data, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clear -> {
                normalDialog = AlertDialog.Builder(this)
                normalDialog.setTitle(getString(R.string.deldata))
                normalDialog.setMessage(getString(R.string.isdeldata))
                normalDialog.setPositiveButton(android.R.string.ok) { _, _ ->
                    MyApplication.dao.delAllData()
                }
                normalDialog.setNegativeButton(android.R.string.cancel, null)
                normalDialog.show()
            }

            R.id.export_data -> {
                com.github.h4de5ing.filepicker.DialogUtils.selectDir(
                    this,
                    getString(R.string.export_data), true
                ) { files ->
                    val progressDialog = ProgressDialog(this)
                    showProgressDialog(
                        progressDialog,
                        getString(R.string.Export),
                        getString(R.string.Exporting_data)
                    )
                    output(files[0] + File.separator + files[1], progressDialog)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun output(path: String, progressDialog: ProgressDialog) {
        Thread {
            val count: Long = MyApplication.dao.getLastID()
            val num = 100000
            var start: Long = MyApplication.dao.getFirstID()
            var end: Long
            var res = false
            while (start <= count) {
                end = if (start + num > count) count else start + num
                val dataList: List<String> = MyApplication.dao.selectAllData3(start, end)
                res = File(path).append("${dataList.map { "\n$it" }}")
                start += num
            }
            runOnUiThread {
                try {
                    progressDialog.dismiss()
                } catch (_: Exception) {
                }
            }
            showToast(getString(if (res) R.string.export_data_completed else R.string.export_data_failed))
        }.start()
    }
}