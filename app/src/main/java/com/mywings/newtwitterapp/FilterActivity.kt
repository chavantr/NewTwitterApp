package com.mywings.newtwitterapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.mywings.newtwitterapp.model.TwitterComments
import com.mywings.newtwitterapp.process.FetchFilterValue
import com.mywings.newtwitterapp.process.OnFilterDataListener
import com.mywings.newtwitterapp.process.ProgressDialogUtil
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : AppCompatActivity(), OnFilterDataListener {

    private lateinit var progressDialogUtil: ProgressDialogUtil

    private var mResult: ArrayList<TwitterComments>? = null
    private lateinit var mResultNext: ArrayList<TwitterComments>
    private var searchCategory: String? = null

    private var services = arrayListOf(
        "Room",
        "Price", "Staff", "Service",
        "Food", "Hotel",
        "Historical places", "Beach", "Water park"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        progressDialogUtil = ProgressDialogUtil(this)
        init()

        btnHotel.setOnClickListener {
            searchCategory = services[5]
            startResultActivity(searchCategory)
        }

        btnHistoricalPlaces.setOnClickListener {
            searchCategory = services[6]
            startResultActivity(searchCategory)
        }

        btnBeach.setOnClickListener {
            searchCategory = services[7]
            startResultActivity(searchCategory)
        }

        btnWaterParks.setOnClickListener {
            searchCategory = services[8]
            startResultActivity(searchCategory)
        }

        btnRoom.setOnClickListener {
            searchCategory = services[0]
            startResultActivity(searchCategory)
        }

        btnPrice.setOnClickListener {
            searchCategory = services[1]
            startResultActivity(searchCategory)
        }

        btnStaff.setOnClickListener {
            searchCategory = services[2]
            startResultActivity(searchCategory)
        }

        btnServices.setOnClickListener {
            searchCategory = services[3]
            startResultActivity(searchCategory)
        }

        btnFood.setOnClickListener {
            searchCategory = services[4]
            startResultActivity(searchCategory)
        }

        btnAllAspects.setOnClickListener {

            newType = mResult!!

            val popupMenu = PopupMenu(this@FilterActivity, it)
            popupMenu.menuInflater.inflate(R.menu.menu_positive_negative, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_positive -> {
                        val intent = Intent(this@FilterActivity, ViewGraphActivity::class.java)
                        intent.putExtra("flag", true)
                        startActivity(intent)
                        return@setOnMenuItemClickListener true
                    }
                    else -> {
                        val intent = Intent(this@FilterActivity, ViewGraphActivity::class.java)
                        intent.putExtra("flag", false)
                        startActivity(intent)
                        return@setOnMenuItemClickListener true
                    }
                }
            }
            popupMenu.show()
        }
    }

    private fun init() {
        progressDialogUtil.show()
        val fetchFilterValue = FetchFilterValue()
        fetchFilterValue.setOnFilterListener(this, "")
    }

    override fun onFilter(result: ArrayList<TwitterComments>?) {
        progressDialogUtil.hide()

        if (null != result && !result?.isNullOrEmpty()) {
            mResult = ArrayList()
            result.forEach {
                if (null != it && it.comment?.contains(intent.getStringExtra("keyword"), true)!!) {
                    mResult?.add(it)
                }
            }
        }
    }

    private fun searchServices(keyword: String?): ArrayList<TwitterComments> {
        mResultNext = ArrayList()

        for (node in mResult!!) {
            if (node.classLabel?.toLowerCase() == keyword?.toLowerCase()) {
                mResultNext.add(node)
            }
        }

        return mResultNext
    }


    private fun startResultActivity(keyword: String?) {
        newType = searchServices(keyword)
        val intent = Intent(this@FilterActivity, ResultServiceActivity::class.java)
        intent.putExtra("next", keyword)
        startActivity(intent)
    }

    companion object {
        lateinit var newType: ArrayList<TwitterComments>
    }
}
