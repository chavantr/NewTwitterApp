package com.mywings.newtwitterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mywings.newtwitterapp.process.FetchFilterValue
import com.mywings.newtwitterapp.process.OnFilterDataListener
import com.mywings.newtwitterapp.process.ProgressDialogUtil

class FilterActivity : AppCompatActivity(), OnFilterDataListener {

    private lateinit var progressDialogUtil: ProgressDialogUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        progressDialogUtil = ProgressDialogUtil(this)
        init()
    }

    private fun init() {
        progressDialogUtil.show()
        val fetchFilterValue = FetchFilterValue()
        fetchFilterValue.setOnFilterListener(this, "")
    }

    override fun onFilter(result: ArrayList<String>?) {
        progressDialogUtil.hide()
    }
}
