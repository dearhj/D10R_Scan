package com.scanner.d10r.hardware.symbology

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.entity.node.BaseNode
import com.scanner.d10r.hardware.base.BaseBackActivity
import com.scanner.d10r.hardware.util.scanModule
import com.scanner.d10r.hardware.util.setOnChangeUsb
import com.scannerd.d10r.hardware.R
import com.scannerd.d10r.hardware.databinding.ActivitySymbologyNewBinding

class SymbologyHrNewLandActivity : BaseBackActivity() {
    private lateinit var binding: ActivitySymbologyNewBinding
    private val adapter: NodeTreeAdapter = NodeTreeAdapter()
    private var symbologyConfig: MutableList<CodeObj>? = null
    private val list = ArrayList<BaseNode>()
    private var activityFlag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySymbologyNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        if (scanModule == 1) symbologyConfig = getAllConfig()
        else if (scanModule == 2) symbologyConfig = getAllEM3100Config()
        else if (scanModule == 3) symbologyConfig = getAllME11Config()

        symbologyConfig!!.forEach { codeObj ->
            val secondList = ArrayList<BaseNode>()
            codeObj.functions.forEach { s ->
                secondList.add(
                    SecondNode(
                        s.name,
                        s.default,
                        s.query,
                        s.uiList,
                        s.uiValue,
                        s.min,
                        s.max,
                        s.step,
                        s.length,
                        s.addValue,
                        null
                    )
                )
            }
            list.add(FirstNode(codeObj.codeName, secondList))
        }

        adapter.setList(list)
        setOnChangeUsb {
            if (activityFlag) {
                showToast(getString(R.string.activity_close))
                Handler(Looper.getMainLooper()).postDelayed({ finish() }, 1000)
            }
        }
    }

    override fun onResume() {
        activityFlag = true
        super.onResume()
    }

    override fun onPause() {
        activityFlag = false
        super.onPause()

    }
}