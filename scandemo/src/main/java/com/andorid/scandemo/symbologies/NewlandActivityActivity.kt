package com.andorid.scandemo.symbologies

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andorid.scandemo.R
import com.andorid.scandemo.adapter.HonewellQrAdapter
import com.andorid.scandemo.databinding.ActivityRecyclerviewBinding
import com.andorid.scandemo.expandable.BookAdapter
import com.andorid.scandemo.expandable.GroupTitle
import com.andorid.scandemo.expandable.SimplePaddingDecoration
import com.andorid.scandemo.utils.*
import com.andorid.scannerlib.CodeObj
import com.andorid.scannerlib.bean.LengthFunction
import com.andorid.scannerlib.getNewland
import com.h4de5ing.expandablerecyclerview.bean.RecyclerViewData
import com.h4de5ing.expandablerecyclerview.listener.OnRecyclerViewListener

@SuppressLint("UseCompatLoadingForDrawables")
class NewlandActivityActivity : BaseActivity(), OnRecyclerViewListener.OnItemClickListener {
    private var datas: MutableList<RecyclerViewData<*, *>>? = ArrayList()
    private var adapter: BookAdapter? = null
    private var show: TextView? = null
    private var notSupport = ""
    private var notExist = ""
    private val mainMenus: MutableList<CodeObj> = getNewland()
    private lateinit var binding: ActivityRecyclerviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        for ((title, list) in mainMenus) {
            val bean: MutableList<GroupTitle?> = java.util.ArrayList()
            if (list.isNotEmpty()) {
                for (loginMenu in list) {
                    bean.add(GroupTitle(loginMenu.functionName))
                }
            }
            datas!!.add(RecyclerViewData<Any?, Any?>(title, bean as List<Any?>?, false))
        }
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

    override fun onResume() {
        super.onResume()
        setSPChangeListener {//开始解析串口数据
            try {
                var currentIndex = -1
                "数据回来了${DataUtil.bytes2HexString(it)} ${String(it)}".logE()
                val type = it.copyOfRange(it.size - 3, it.size - 2)
                when {
                    type[0] == 0x05.toByte() -> showToast(notExist)
                    type[0] == 0x15.toByte() -> showToast(notSupport)
                    type[0] == 0x06.toByte() -> {
                        val tag = it.copyOfRange(7, 10)
                        //解析出所有的值
                        val listFun = mutableListOf<Pair<String, String>>()
                        var last06 = 10
                        it.forEachIndexed { index, byte ->
                            if (byte == 0x06.toByte()) {
                                if (last06 > 10) last06 += 2
                                val data = it.copyOfRange(last06, index)
                                ("解析出了:${String(data.copyOfRange(0, 3))}  " +
                                        " ${String(data.copyOfRange(3, data.size))}").logD()
                                listFun.add(
                                    Pair(
                                        String(data.copyOfRange(0, 3)),
                                        String(data.copyOfRange(3, data.size))
                                    )
                                )
                                last06 = index
                            }
                        }
                        //更新到UI上
                        tempPairs.forEachIndexed { index, function ->
                            listFun.forEach {
                                if (function.second.equals(
                                        "${String(tag)}${it.first}${it.second}", true
                                    )
                                ) {
                                    currentIndex = index
                                }
                            }
                        }
                        //如果反正的是一个单纯的值，直接更新UI
                        updateIndex(currentIndex)
                        //如果返回的是一个范围
                        updateValue(listFun)
                    }

                    else -> {
                        "未知命令${DataUtil.bytes2HexString(it)}".logD()
                    }
                }
            } catch (e: Exception) {
                "尴尬了 ${DataUtil.bytes2HexString(it)}->${e.message} ".logE()
                e.printStackTrace()
            }
        }
    }

    private fun updateShow(message: String) {
        runOnUiThread {
            try {
                show?.apply {
                    this.append(message)
                    val offset = this.lineCount * this.lineHeight - this.height + 10
                    this.scrollTo(0, offset.coerceAtLeast(0))
                }
            } catch (_: Exception) {
            }
        }
    }

    private fun updateValue(list: MutableList<Pair<String, String>>) {
        runOnUiThread {
            try {
                val pair = list[0]
                val value = pair.second
                currentValue = value.toInt()
                currentValueTv?.text = value
                seekBar?.progress = currentValue
            } catch (_: Exception) {
            }
        }
    }

    private fun updateIndex(updateIndex: Int) {
        try {
            if (updateIndex > -1) {
                tripleList.clear()
                tempPairs.forEachIndexed { index, it ->
                    tripleList.add(
                        Triple(
                            it.first,
                            it.second,
                            updateIndex == index
                        )
                    )
                }
                runOnUiThread { subAdapter.setNewInstance(tripleList.toMutableList()) }
            }
        } catch (_: Exception) {
        }
    }

    override fun onGroupItemClick(position: Int, groupPosition: Int, view: View?) {
    }

    override fun onChildItemClick(
        position: Int,
        groupPosition: Int,
        childPosition: Int,
        view: View?
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

    var currentValueTv: TextView? = null
    var seekBar: SeekBar? = null
    var currentValue = 0

    private fun showInputDialog(
        functionName: String,
        lengthFunction: LengthFunction
    ) {
        try {
            //queryRange(lengthFunction.command)
            //delayed(50) { query(lengthFunction.command) }
            query(lengthFunction.command)
            val alertBuilder = AlertDialog.Builder(this)
            alertBuilder.setIcon(R.mipmap.ic_launcher)
            alertBuilder.setTitle(functionName)
            val v = View.inflate(this, R.layout.dialog_slider, null)
            currentValueTv = v.findViewById(R.id.current_value)
            seekBar = v.findViewById(R.id.seek_bar)
            seekBar?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    it.min = lengthFunction.min
                }
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
                send2Newland("${lengthFunction.command}${currentValue}.")
            }
            alertBuilder.create().show()
            mCurrentQueryString = lengthFunction.command
        } catch (_: Exception) {
        }
    }

    private var mCurrentQueryString = ""
    private val tripleList = mutableListOf<Triple<String, String, Boolean>>()
    private val subAdapter = HonewellQrAdapter()
    private var tempPairs = mutableListOf<Pair<String, String>>()
    private fun showDialog(
        functionName: String,
        functionList: List<Pair<String, String>>,
        query: String?
    ) {
        try {
            if (!TextUtils.isEmpty(query)) query("$query")
            tripleList.clear()
            tempPairs.clear()
            tempPairs.addAll(functionList)
            val alertBuilder = AlertDialog.Builder(this)
            alertBuilder.setIcon(R.mipmap.ic_launcher)
            alertBuilder.setTitle(functionName)
            val v = View.inflate(this, R.layout.activity_recyclerview, null)
            val vList = v.findViewById<RecyclerView>(R.id.recyclerView)
            vList.layoutManager = LinearLayoutManager(this)
            vList.adapter = subAdapter
            functionList.forEach { tripleList.add(Triple(it.first, it.second, false)) }
            subAdapter.setNewInstance(tripleList.toMutableList())
            subAdapter.setOnItemClickListener { _, _, position -> send2Newland(functionList[position].second) }
            alertBuilder.setView(v)
            alertBuilder.setNegativeButton(R.string.close, null)
            alertBuilder.create().show()
            mCurrentQueryString = "$query"
        } catch (_: Exception) {
        }
    }

    // *(2A) 查询当前设置   &(26) 查询出厂默认设置  ^(5E) 查询取值范围
    private fun queryRange(query: String) {
        send2Newland("${query}^")
    }

    private fun query(query: String) {
        send2Newland("${query}*")
    }

    private fun queryDefault(query: String) {
        send2Newland("${query}&")
    }
}