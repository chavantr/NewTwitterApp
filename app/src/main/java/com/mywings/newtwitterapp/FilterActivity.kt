package com.mywings.newtwitterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mywings.newtwitterapp.process.OnFilterDataListener

class FilterActivity : AppCompatActivity(), OnFilterDataListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
    }

    override fun onFilter(result: ArrayList<String>?) {

    }
}
