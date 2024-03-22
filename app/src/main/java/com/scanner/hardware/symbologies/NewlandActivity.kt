package com.scanner.hardware.symbologies

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.scanner.hardware.databinding.ActivitySymbologiesNewlandBinding
import com.scanner.hardware.expandable.BookAdapter
import com.scanner.hardware.expandable.GroupTitle
import com.scanner.hardware.expandable.SimplePaddingDecoration
import com.scanner.hardware.util.*

/**
 * 发送指令格式
Prefix(固定6个字节) 7E 01 30 30 30 30
Storeage(1个字节@永久设置40 #暂时设置23)
Tag(表示设置类别3个字节) 例如128 -> 31 32 38
SubTag(3个字节) ENA->45 4E 41
Data 表示为数据，数据的格式由 Tag、SubTag 共同决定。特殊Data字符  *(2A) 查询当前设置   &(26) 查询出厂默认设置  ^(5E) 查询取值范围
Suffix(2个字节，;<ETX> ->3B 03)
7E 01 30 30 30 30 40 31 32 38 45 4E 41 2A 3B 03


接收指令格式
02 01 30 30 30 30 40 31 32 38 45 4E 41 31 06 3B 03
Prefix(固定6个字节 <STX><SOH>0000->02 01 30 30 30 30)
Storeage(1个字节@永久设置40 #暂时设置23)
Tag(表示设置类别3个字节) 例如128 -> 31 32 38
SubTag(3个字节) ENA->45 4E 41
Data 表示数据
设备应答(1个字节<ACK>06操作成功  <NAK>15数据的值不在支持范围 <ENQ>05 设置类别或功能项不存在)
Suffix(2个字节，;<ETX> ->3B 03)
 */
@SuppressLint("UseCompatLoadingForDrawables")
class NewlandActivity : BaseBackActivity(), OnRecyclerViewListener.OnItemClickListener {
    private var datas: MutableList<RecyclerViewData<*, *>>? = ArrayList()
    private var adapter: BookAdapter? = null
    private var hexReceive = false
    private var notSupport = ""
    private var notExist = ""
    private lateinit var binding: ActivitySymbologiesNewlandBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySymbologiesNewlandBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (isDebug()) {
            binding.controller.visibility = View.VISIBLE
            binding.clearText.setOnClickListener { binding.result.text = "" }
            binding.cbHexReceive.setOnCheckedChangeListener { _, isChecked ->
                hexReceive = isChecked
            }
            binding.result.movementMethod = ScrollingMovementMethod()
            binding.send.setOnClickListener {
                val inputStr = binding.inputCommand.text.toString()
                if (!TextUtils.isEmpty(inputStr)) send2Newland(inputStr)
            }
        }
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

    override fun onResume() {
        super.onResume()
        mirror()
    }

    private fun updateShow(message: String) {
        runOnUiThread {
            try {
                binding.result.append(message)
                val offset =
                    binding.result.lineCount * binding.result.lineHeight - binding.result.height + 10
                binding.result.scrollTo(0, offset.coerceAtLeast(0))
            } catch (_: Exception) {
            }
        }
    }

    private fun mirror() {
        setReceived {
            try {
                if (hexReceive) updateShow("r:${it.toHexString()}\n")
                else updateShow("r:${String(it)}\n")
                val currentIndex: Int
                "数据回来了${it.toHexString()} ${String(it)}".logD()
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
                                "解析出了:${
                                    String(
                                        data.copyOfRange(
                                            0, 3
                                        )
                                    )
                                }  ${String(data.copyOfRange(3, data.size))}".logD()
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
                        currentIndex =
                            tempPairs.indexOfLast { it1 -> it1.second == "${String(tag)}${listFun[0].first}${listFun[0].second}" }
                        //如果反正的是一个单纯的值，直接更新UI
                        updateIndex(currentIndex)
                        //如果返回的是一个范围
                        updateValue(listFun)
                    }

                    else -> {
                        "未知命令${it.toHexString()}".logD()
                    }
                }
            } catch (e: Exception) {
                "尴尬了 ${it.toHexString()}->${e.message} ".logE()
                e.printStackTrace()
            }
        }
    }

    //需要解析 1|2-5 等值
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
                            it.first, it.second, updateIndex == index
                        )
                    )
                }
                runOnUiThread { subAdapter.setNewInstance(tripleList.toMutableList()) }
            }
        } catch (_: Exception) {
        }
    }

    private val mainMenus: MutableList<CodeObj> = getNewland()
    private fun initBooks() {
        datas?.clear()
        for ((title, list) in mainMenus) {
            val bean: MutableList<GroupTitle?> = ArrayList()
            if (list.isNotEmpty()) {
                for (loginMenu in list) bean.add(GroupTitle(loginMenu.functionName))
            }
            datas?.add(RecyclerViewData<Any?, Any?>(title, bean as List<Any?>?, false))
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

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showInputDialog(
        functionName: String, lengthFunction: LengthFunction
    ) {
        try {
            query(lengthFunction.command)
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
                send2Newland("INTERF0")
                delayed(10) { send2Newland("${lengthFunction.command}${currentValue}.") }
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
        functionName: String, functionList: List<Pair<String, String>>, query: String?
    ) {
        try {
            if (!TextUtils.isEmpty(query)) query("$query")
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
            subAdapter.setOnItemClickListener { _, _, position -> send2Newland(functionList[position].second) }
            alertBuilder.setView(v)
            alertBuilder.setNegativeButton(R.string.close, null)
            alertBuilder.create().show()
            mCurrentQueryString = "$query"
        } catch (_: Exception) {
        }
    }

    // *(2A) 查询当前设置   &(26) 查询出厂默认设置  ^(5E) 查询取值范围
    private fun query(query: String) {
        send2Newland("${query}*")
    }


}