package com.scanner.hardware.symbologies

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.h4de5ing.base.delayed
import com.github.h4de5ing.base.toHexString
import com.github.h4de5ing.baseui.change
import com.github.h4de5ing.baseui.logD
import com.github.h4de5ing.baseui.logE
import com.h4de5ing.expandablerecyclerview.bean.RecyclerViewData
import com.h4de5ing.expandablerecyclerview.listener.OnRecyclerViewListener
import com.scanner.hardware.R
import com.scanner.hardware.adapter.HonewellQrAdapter
import com.scanner.hardware.base.BaseBackActivity
import com.scanner.hardware.bean.LengthFunction
import com.scanner.hardware.databinding.ActivityRecyclerviewBinding
import com.scanner.hardware.expandable.BookAdapter
import com.scanner.hardware.expandable.GroupTitle
import com.scanner.hardware.expandable.SimplePaddingDecoration
import com.scanner.hardware.util.*
import java.util.*

//霍尼韦尔二维码
@SuppressLint("UseCompatLoadingForDrawables")
class HoneywellQrActivity : BaseBackActivity(), OnRecyclerViewListener.OnItemClickListener {
    private var datas: MutableList<RecyclerViewData<*, *>>? = ArrayList()
    private var adapter: BookAdapter? = null
    private var notSupport = ""
    private var notExist = ""
    private lateinit var binding: ActivityRecyclerviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBooks()
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.addItemDecoration(SimplePaddingDecoration(this))
        adapter = BookAdapter(this, datas)
        adapter?.setOnItemClickListener(this)
        binding.recyclerView.adapter = adapter
        notSupport = getString(R.string.not_supported)
        notExist = getString(R.string.not_exist)
    }

    private fun mirror() {
        setReceived {
            try {
                "返回来原始数据是:${it.toHexString()} ${String(it)}".logD()
                var currentIndex = -1
                if (it[it.size - 2] == (0x15.toByte()) && it[it.size - 1] == (0x2E.toByte())) {//15 2E结尾表示不支持的命令
                    runOnUiThread { showToast(notSupport) }
                } else if (it[it.size - 2] == (0x05.toByte()) && it[it.size - 1] == (0x2E.toByte())) {//05 2E结尾表示不支持的命令
                    runOnUiThread { showToast(notExist) }
                } else {//06 2E结尾表示命令正确
                    if (!TextUtils.isEmpty(mCurrentQueryString)) {
                        //"返回来原始数据是:${DataUtil.bytes2HexString(it)}  查询命令${mCurrentQueryString}".logD()
                        //41 5A 54 45 4E 41 命令开头  41 5A 54 44 43 50       35 31        06 2E
                        //31 数据值
                        //06 2E 命令结尾
                        val startByteArray =
                            mCurrentQueryString.uppercase(Locale.getDefault()).toByteArray()
                        val stopByteArray = byteArrayOf(0x06, 0x2E)
                        val value = it.copyOfRange(
                            startByteArray.size, it.size - stopByteArray.size
                        )
                        "返回来的值:${value.toHexString()}  -> ${mCurrentQueryString}${
                            String(
                                value
                            )
                        }  $tempPairs".logD()
                        tempPairs.forEachIndexed { index, function ->
                            if (function.second.equals(
                                    "${mCurrentQueryString}${String(value)}.", true
                                )
                            ) {
                                currentIndex = index
                            }
                        }
                        "找到了没有 $currentIndex".logD()
                        if (currentIndex == -1) {//不规则命令返回解析
                            val newValue = it.copyOfRange(0, it.size - stopByteArray.size)
                            tempPairs.forEachIndexed { index, function ->
                                if (function.second.equals("${String(newValue)}.", true)) {
                                    "另外一种可能被找到了${index}".logD()
                                    currentIndex = index
                                }
                            }
                        }
                        //如果是多个选项，就将返回结果选中
                        updateIndex(currentIndex)
                        //如果返回的是值，将解析的值更新到UI
                        updateValue(String(value))
                    }
                }
            } catch (e: Exception) {
                "发生了什么事情?${e.message}".logE()
                e.printStackTrace()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mirror()
    }

    private fun updateIndex(updateIndex: Int) {
        try {
            if (updateIndex > -1) {
                tripleList.clear()
                tempPairs.forEachIndexed { index, it ->
                    tripleList.add(
                        Triple(
                            it.first, it.second, updateIndex == index
                        )
                    )
                }
                runOnUiThread { subAdapter.setNewInstance(tripleList.toMutableList()) }
            }
        } catch (_: Exception) {
        }
    }

    private fun updateValue(value: String) {
        runOnUiThread {
            try {
                if (!TextUtils.isEmpty(value)) {
                    currentValue = value.toInt()
                    currentValueTv?.text = value
                    seekBar?.progress = currentValue
                }
            } catch (_: Exception) {
            }
        }
    }

    private val mainMenus: MutableList<CodeObj> = getHuoNi()
    private fun initBooks() {
        datas!!.clear()
        if (isDebug()) mainMenus.addAll(addDebugCommand())
        for ((title, list) in mainMenus) {
            val bean: MutableList<GroupTitle?> = ArrayList()
            if (list.isNotEmpty()) {
                for (loginMenu in list) {
                    bean.add(GroupTitle(loginMenu.functionName))
                }
            }
            datas!!.add(RecyclerViewData<Any?, Any?>(title, bean as List<Any?>?, false))
        }
    }

    override fun onGroupItemClick(position: Int, groupPosition: Int, view: View?) {
    }

    override fun onChildItemClick(
        position: Int, groupPosition: Int, childPosition: Int, view: View?
    ) {
        val codeObj = mainMenus[groupPosition]
        val child = codeObj.functions[childPosition]
        val functionName = child.functionName
        val functionList = child.functionList
        val functionQuery = child.queryString
        val functionLength = child.lengthFunction
        runOnUiThread {
            if (functionLength != null) {
                showInputDialog(functionName, functionLength)
            } else {
                showDialog(functionName, functionList, functionQuery)
            }
        }
    }

    private var currentValueTv: TextView? = null
    private var seekBar: SeekBar? = null
    private var currentValue = 0

    private fun showInputDialog(
        functionName: String, lengthFunction: LengthFunction
    ) {
        try {
            mCurrentQueryString = lengthFunction.command
            delayed(50) { sendString2SP("${lengthFunction.command}?.") }
            val alertBuilder = AlertDialog.Builder(this)
            alertBuilder.setIcon(R.mipmap.icon_tm)
            alertBuilder.setTitle(functionName)
            val v = View.inflate(this, R.layout.dialog_slider, null)
            currentValueTv = v.findViewById(R.id.current_value)
            seekBar = v.findViewById(R.id.seek_bar)
            seekBar?.let {
                it.min = lengthFunction.min
                it.max = lengthFunction.max
                seekBar?.change { progress ->
                    currentValue = progress
                    currentValueTv?.text = "$currentValue"
                    seekBar?.progress = progress
                }
            }
            val minValue = v.findViewById<TextView>(R.id.min_value)
            val maxValue = v.findViewById<TextView>(R.id.max_value)
            minValue.text = "${lengthFunction.min}"
            maxValue.text = "${lengthFunction.max}"
            alertBuilder.setView(v)
            alertBuilder.setNegativeButton(android.R.string.cancel, null)
            alertBuilder.setPositiveButton(android.R.string.ok) { _, _ ->
                sendString2SP("${lengthFunction.command}${currentValue}.")
            }
            alertBuilder.create().show()
        } catch (_: Exception) {
        }
    }

    private var mCurrentQueryString = ""
    private val tripleList = mutableListOf<Triple<String, String, Boolean>>()
    private val subAdapter = HonewellQrAdapter()
    private var tempPairs = mutableListOf<Pair<String, String>>()
    private fun showDialog(
        functionName: String, functionList: List<Pair<String, String>>, query: String?
    ) {
        try {
            if (!TextUtils.isEmpty(query)) {
                //如果是查询默认命令就不发送
                "$functionName  ${functionList.size} 命令大小".logD()
                mCurrentQueryString = "$query"
                if (functionList.size > 1) {
                    delayed(50) { sendString2SP("${query}?.") }
                }
            }
            tripleList.clear()
            tempPairs.clear()
            tempPairs.addAll(functionList)
            val alertBuilder = AlertDialog.Builder(this)
            alertBuilder.setIcon(R.mipmap.icon_tm)
            alertBuilder.setTitle(functionName)
            val v = View.inflate(this, R.layout.activity_recyclerview, null)
            val vList = v.findViewById<RecyclerView>(R.id.recyclerView)
            vList.layoutManager = LinearLayoutManager(this)
            vList.adapter = subAdapter
            functionList.forEach { tripleList.add(Triple(it.first, it.second, false)) }
            subAdapter.setNewInstance(tripleList.toMutableList())
            subAdapter.setOnItemClickListener { _, _, position -> sendString2SP(functionList[position].second) }
            alertBuilder.setView(v)
            alertBuilder.setNegativeButton(R.string.close, null)
            alertBuilder.create().show()
        } catch (_: Exception) {
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.togglesetting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.open_all -> toggle(true)
            R.id.close_all -> toggle(false)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toggle(toggle: Boolean) {
        confirmDialogMessage(
            this,
            if (toggle) getString(R.string.open_all_code) else getString(R.string.close_all_code)
        ) {
            val progressDialog = ProgressDialog.show(this, "", "progress...")
            sendString2SP("allena${if (toggle) "1" else "0"}.")
            delayed(200) { runOnUiThread(progressDialog::dismiss) }
        }
    }
}