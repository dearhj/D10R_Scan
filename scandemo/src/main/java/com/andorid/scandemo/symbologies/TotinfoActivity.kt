package com.andorid.scandemo.symbologies

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
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
import com.andorid.scannerlib.getToinfoEN
import com.h4de5ing.expandablerecyclerview.bean.RecyclerViewData
import com.h4de5ing.expandablerecyclerview.listener.OnRecyclerViewListener

//图腾码制设置
@SuppressLint("UseCompatLoadingForDrawables")
class TotinfoActivity : BaseActivity(), OnRecyclerViewListener.OnItemClickListener {
    private var datas: MutableList<RecyclerViewData<*, *>>? = ArrayList()
    private var adapter: BookAdapter? = null
    private var changeIndex = -1
    private val mainMenus: MutableList<CodeObj> = getToinfoEN()
    private lateinit var binding: ActivityRecyclerviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        for ((title, list) in mainMenus) {
            val bean: MutableList<GroupTitle?> = ArrayList()
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
    }

    override fun onResume() {
        super.onResume()
        setSPChangeListener { data ->//开始解析串口数据
            try {
                when {
                    DataUtil.int2bytes2("05D1000002FF28").contentEquals(data) -> showToast(
                        getString(R.string.command_failed)
                    )

                    DataUtil.int2bytes2("04D00000FF2C").contentEquals(data) -> {
                        //设置成功回显
                        updateIndex(changeIndex)
                        showToast(getString(R.string.command_success))
                    }

                    else -> {//查询命令返回或者是扫描数据
                        var currentIndex = -1
                        "串口返回数据了:${DataUtil.bytes2HexString(data)}  $tripleList".logD()
                        tempPairs.forEachIndexed { index, function ->
                            val spCommand = DataUtil.bytes2HexString(data)
                            val commandTypeString = spCommand!!.split(" ")[data.size - 4]
                            val commandValueString = spCommand.split(" ")[data.size - 3]

                            val functionCommand = function.second.split(" ")
                            val functionType = functionCommand[functionCommand.size - 4]
                            val functionValue = functionCommand[functionCommand.size - 3]

                            ("类型:${commandTypeString} $commandValueString   $functionType $functionValue").logD()
                            if (commandTypeString == functionType && commandValueString == functionValue) {
                                currentIndex = index
                                ("我们找到了命令返回:${function.first} $changeIndex").logD()
                            }
                        }
                        updateIndex(currentIndex)
                    }
                }
            } catch (e: Exception) {
                ("发生异常 ${e.message}").logE()
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
                sendByteArray2SP0x00(DataUtil.int2bytes2(query))
            }
            val alertBuilder = AlertDialog.Builder(this)
            alertBuilder.setIcon(R.mipmap.ic_launcher)
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
                    DataUtil.int2bytes2(functionList[position].second.replace(" ", ""))
                )
            }
            alertBuilder.setView(v)
            alertBuilder.setNegativeButton(R.string.close, null)
            alertBuilder.create().show()
        } catch (_: Exception) {
        }
    }
}