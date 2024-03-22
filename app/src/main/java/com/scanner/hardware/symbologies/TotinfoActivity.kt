package com.scanner.hardware.symbologies

import android.annotation.SuppressLint
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
import com.scanner.hardware.base.BaseBackActivity
import com.scanner.hardware.databinding.ActivityRecyclerviewBinding
import com.scanner.hardware.expandable.BookAdapter
import com.scanner.hardware.expandable.GroupTitle
import com.scanner.hardware.expandable.SimplePaddingDecoration
import com.scanner.hardware.util.*

//图腾针对难扫条码的处理方法:依次扫【允许扫描配置条码 1040601】【等级2 3030EC2】【禁止扫描配置条码 1040600】
@SuppressLint("UseCompatLoadingForDrawables")
class TotinfoActivity : BaseBackActivity(), OnRecyclerViewListener.OnItemClickListener {
    private var datas: MutableList<RecyclerViewData<*, *>>? = ArrayList()
    private var adapter: BookAdapter? = null
    private var changeIndex = -1
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

    override fun onResume() {
        super.onResume()
        mirror()
    }

    private fun mirror() {
        setReceived { data ->
            try {
                when {
                    "05D1000002FF28".toHexByteArray().contentEquals(data) -> showToast(
                        getString(R.string.command_failed)
                    )

                    "04D00000FF2C".toHexByteArray().contentEquals(data) -> {
                        updateIndex(changeIndex)
                        showToast(getString(R.string.command_success))
                    }

                    else -> {//查询命令返回或者是扫描数据
                        var currentIndex = -1
                        "串口返回数据了:${data.toHexString()}  $tripleList".logD()
                        tempPairs.forEachIndexed { index, function ->
                            val spCommand = data.toHexString()
                            val commandTypeString = spCommand.split(" ")[data.size - 4]
                            val commandValueString = spCommand.split(" ")[data.size - 3]

                            val functionCommand = function.second.split(" ")
                            val functionType = functionCommand[functionCommand.size - 4]
                            val functionValue = functionCommand[functionCommand.size - 3]

                            "类型:${commandTypeString} $commandValueString   $functionType $functionValue".logD()
                            if (commandTypeString == functionType && commandValueString == functionValue) {
                                currentIndex = index
                                "我们找到了命令返回:${function.first} $changeIndex".logD()
                            }
                        }
                        updateIndex(currentIndex)
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
        mainMenus.addAll(getToinfoEN())
        datas?.clear()
        for ((title, list) in mainMenus) {
            val bean: MutableList<GroupTitle?> = ArrayList()
            if (list.isNotEmpty()) for (loginMenu in list) bean.add(GroupTitle(loginMenu.functionName))
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
        showDialog(functionName, functionList, functionQuery)
    }

    private val tripleList = mutableListOf<Triple<String, String, Boolean>>()
    private val subAdapter = HonewellQrAdapter()
    private var tempPairs = mutableListOf<Pair<String, String>>()
    private fun showDialog(
        functionName: String, functionList: List<Pair<String, String>>, query: String?
    ) {
        try {
            tripleList.clear()
            tempPairs.clear()
            tempPairs.addAll(functionList)
            if (!TextUtils.isEmpty(query)) delayed(50) {
                sendByteArray2SP0x00(query!!.toHexByteArray())
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
                sendByteArray2SP0x00(
                    functionList[position].second.replace(" ", "").toHexByteArray()
                )
            }
            alertBuilder.setView(v)
            alertBuilder.setNegativeButton(R.string.close, null)
            alertBuilder.create().show()
        } catch (_: Exception) {
        }
    }
}