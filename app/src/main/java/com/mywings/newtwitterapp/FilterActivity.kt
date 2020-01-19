package com.mywings.newtwitterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mywings.newtwitterapp.algorithm.RemoveStopWords
import com.mywings.newtwitterapp.algorithm.Stemmer
import com.mywings.newtwitterapp.model.TwitterComments
import com.mywings.newtwitterapp.process.FetchFilterValue
import com.mywings.newtwitterapp.process.OnFilterDataListener
import com.mywings.newtwitterapp.process.ProgressDialogUtil
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : AppCompatActivity(), OnFilterDataListener {

    private lateinit var progressDialogUtil: ProgressDialogUtil

    private var mResult: ArrayList<TwitterComments>? = null

    private var services = arrayListOf(
        "Food",
        "Room", "Location", "Staff",
        "Service", "Price",
        "Cleanliness", "Bar"
    )

    private var sortedData: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        progressDialogUtil = ProgressDialogUtil(this)
        init()
        btnRoom.setOnClickListener {
            startResultActivity(services[1])
        }
        btnFood.setOnClickListener {
            startResultActivity(services[0])
        }
        btnService.setOnClickListener {
            startResultActivity(services[4])
        }
        btnPrice.setOnClickListener {
            startResultActivity(services[5])
        }
        btnCleanliness.setOnClickListener {
            startResultActivity(services[6])
        }
        btnLocation.setOnClickListener {
            startResultActivity(services[2])
        }
        btnStaff.setOnClickListener {
            startResultActivity(services[3])
        }
        btnBar.setOnClickListener {
            startResultActivity(services[7])
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
            val removeStopWords = RemoveStopWords()
            val stemmer = Stemmer()
            sortedData = ArrayList()
            mResult = ArrayList()
            result.forEach {
                if (null != it && it.comment?.contains(intent.getStringExtra("keyword"))!!) {
                    mResult?.add(it)
                    sortedData?.add(stemmer.getData(removeStopWords.removeWords(it.comment, this@FilterActivity)));
                }
            }
        }
    }

    private fun searchServices(keyword: String?): ArrayList<String> {
        var count: ArrayList<String> = ArrayList()
        mResult?.forEach {
            if (null != it && it?.comment?.contains(keyword!!)!!) {
                count?.add(it?.comment!!)
            }
        }
        return count
    }

    private fun startResultActivity(keyword: String?) {
        resultToDisplay = searchServices(keyword)
        val intent = Intent(this@FilterActivity, ResultServiceActivity::class.java)
        intent.putExtra("next", keyword)
        startActivity(intent)
    }

    companion object {
        lateinit var resultToDisplay: ArrayList<String>
    }
}
