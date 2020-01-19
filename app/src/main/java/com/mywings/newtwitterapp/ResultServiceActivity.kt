package com.mywings.newtwitterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mywings.newtwitterapp.joint.JointAdapter
import kotlinx.android.synthetic.main.activity_result_sevice.*

class ResultServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_sevice)
        lstFinalResult.layoutManager = LinearLayoutManager(this)
        lstFinalResult.adapter = JointAdapter(FilterActivity.resultToDisplay)
    }
}
