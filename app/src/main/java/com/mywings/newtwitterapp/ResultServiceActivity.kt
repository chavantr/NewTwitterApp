package com.mywings.newtwitterapp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.mywings.newtwitterapp.algorithm.RemoveStopWords
import kotlinx.android.synthetic.main.activity_result_sevice.*

class ResultServiceActivity : AppCompatActivity() {

    private var positiveCount: Int = 0
    private var negativeCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_sevice)
        chart.setUsePercentValues(true)

        chart.setExtraOffsets(5f, 10f, 5f, 10f)
        chart.description.isEnabled = true
        val removeStopWords = RemoveStopWords()

        for (node in FilterActivity.newType) {
            negativeCount = negativeCount.plus(removeStopWords.negativeTopics(node.comment, this))
            positiveCount = positiveCount.plus(removeStopWords.positiveTopics(node.comment, this))
        }

       // val newValue = negativeCount * 100 / FilterActivity.newType.size

       // positiveCount = 100 - newValue

        val entries = ArrayList<PieEntry>()

        entries.add(PieEntry(positiveCount.toFloat(), "Positive"))
        entries.add(PieEntry(negativeCount.toFloat(), "Negative"))

        val dataSet = PieDataSet(entries, "Tourism")

        val colors = mutableListOf<Int>(
            Color.BLUE, Color.RED
        )

        //dataSet.sliceSpace = 2f

        dataSet.colors = colors

        val data = PieData(dataSet)

        chart.data = data

        chart.invalidate()

    }
}
