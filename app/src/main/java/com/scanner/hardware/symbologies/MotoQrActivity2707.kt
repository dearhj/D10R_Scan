package com.scanner.hardware.symbologies

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.h4de5ing.base.delayed
import com.github.h4de5ing.base.toHexByteArray
import com.github.h4de5ing.base.toHexString
import com.github.h4de5ing.baseui.logD
import com.github.h4de5ing.baseui.logE
import com.h4de5ing.expandablerecyclerview.bean.RecyclerViewData
import com.h4de5ing.expandablerecyclerview.listener.OnRecyclerViewListener
import com.scanner.hardware.R
import com.scanner.hardware.adapter.HonewellQrAdapter
import com.scanner.hardware.barcodeservice.SerialPortService
import com.scanner.hardware.base.BaseBackActivity
import com.scanner.hardware.bean.Constants
import com.scanner.hardware.databinding.ActivityRecyclerviewBinding
import com.scanner.hardware.expandable.BookAdapter
import com.scanner.hardware.expandable.GroupTitle
import com.scanner.hardware.expandable.SimplePaddingDecoration
import com.scanner.hardware.util.CodeObj
import com.scanner.hardware.util.DataUtil
import com.scanner.hardware.util.getMoTo
import com.scanner.hardware.util.sendByteArray2SP0x00
import com.scanner.hardware.util.setReceived


class MotoQrActivity2707 : BaseBackActivity(), OnRecyclerViewListener.OnItemClickListener {

    private var datas: MutableList<RecyclerViewData<*, *>>? = ArrayList()
    private var adapter: BookAdapter? = null
    private var changeIndex = -1
    private var firstShow = false
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
    }

    private fun mirror() {
        setReceived { data ->
            SerialPortService.readType = Constants.ReadType.SCAN
            if (isShow) try {
                "命令返回:${data.toHexString()}".logE()
                when {
                    "05D1000001FF29".toHexByteArray()
                        .contentEquals(data) -> showToast(getString(R.string.command_failed))

                    "04D00000FF2C".toHexByteArray().contentEquals(data) -> {
                        updateIndex(changeIndex)
                        showToast(getString(R.string.command_success))
                    }

                    else -> {//查询命令返回或者是扫描数据
                        //firstShow值的作用是防止当弹窗弹出以后按扫码键扫码，导致查询结果有误。
                        if (firstShow) {
                            var currentIndex = -1
                            "串口返回数据了:${data.toHexString()}".logD()
                            val resultArray = data.toHexString().split(" ")
                            var status: String? = "null"
                            when (resultArray.size) {
                                14 -> status = resultArray[8]
                                15 -> status = resultArray[9]
                                16 -> status = resultArray[10]
                            }
                            "从查询结果中解析出此码制的值为： $status".logD()
                            tempPairs.forEachIndexed { index, function ->
                                val functionCommand = function.second.split(" ")
                                val value = functionCommand[functionCommand.size - 1]
                                if (value == status) {
                                    currentIndex = index
                                    "我们找到了命令返回:${function.first} $changeIndex".logD()
                                }
                            }
                            updateIndex(currentIndex)
                            firstShow = false
                        }
                    }
                }
            } catch (e: Exception) {
                "发生异常 ${e.message}".logE()
            }
        }
    }

    //串口返回成功之后更新
    private fun updateIndex(updateIndex: Int) {
        try {
            tripleList.clear()
            tempPairs.forEachIndexed { index, it ->
                tripleList.add(
                    Triple(
                        it.first, it.second, updateIndex == index
                    )
                )
            }
            runOnUiThread { subAdapter.setNewInstance(tripleList.toMutableList()) }
        } catch (_: Exception) {
        }
    }

    private val mainMenus = mutableListOf<CodeObj>()
    private fun initBooks() {
        mainMenus.clear()
        mainMenus.addAll(getMoTo())
        datas!!.clear()
        for ((title, list) in mainMenus) {
            val bean: MutableList<GroupTitle?> = ArrayList()
            if (list.isNotEmpty()) for (loginMenu in list) bean.add(GroupTitle(loginMenu.functionName))
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
        showDialog(functionName, functionList, functionQuery)
    }

    private val tripleList = mutableListOf<Triple<String, String, Boolean>>()
    private val subAdapter = HonewellQrAdapter()
    private var tempPairs = mutableListOf<Pair<String, String>>()
    private fun showDialog(
        functionName: String,
        functionList: List<Pair<String, String>>,
        query: String?
    ) {
        try {
            tripleList.clear()
            tempPairs.clear()
            tempPairs.addAll(functionList)
            if (!TextUtils.isEmpty(query)) delayed(50) {
                firstShow = true
                val result = query?.replace(" ", "")
                sendByteArray2SP0x00((result + DataUtil.makeChecksum(result)).toHexByteArray())
            }
            val alertBuilder = AlertDialog.Builder(this)
            alertBuilder.setIcon(R.mipmap.icon_tm)
            alertBuilder.setTitle(functionName)
            val v = View.inflate(this, R.layout.activity_recyclerview, null)
            val vList = v.findViewById<RecyclerView>(R.id.recyclerView)
            vList.layoutManager = LinearLayoutManager(this)
            vList.adapter = subAdapter

            functionList.forEach { tripleList.add(Triple(it.first, it.second, false)) }
            subAdapter.setNewInstance(tripleList.toMutableList())
            subAdapter.setOnItemClickListener { _, _, position ->
                changeIndex = position
                val command = functionList[position].second.replace(" ", "")
                sendByteArray2SP0x00((command + DataUtil.makeChecksum(command)).toHexByteArray())
            }
            alertBuilder.setView(v)
            alertBuilder.setNegativeButton(R.string.close, null)
            alertBuilder.create().show()
        } catch (_: Exception) {
        }
    }

    private var isShow = false
    override fun onResume() {
        super.onResume()
        isShow = true
        mirror()
    }

    override fun onPause() {
        super.onPause()
        isShow = false
    }
}