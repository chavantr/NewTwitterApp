package com.mywings.newtwitterapp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.activity_view_graph.*


class ViewGraphActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_graph)
        chart.setUsePercentValues(true)
        chart.setExtraOffsets(5f, 10f, 5f, 10f)
        chart.description.isEnabled = false

        val entries = ArrayList<PieEntry>()
        //entries.add(PieEntry(5f,"Food"))
        //entries.add(PieEntry(15f,"Food"))
        //entries.add(PieEntry(5f,"Food"))
        //entries.add(PieEntry(25f,"Food"))
        //entries.add(PieEntry(5f,"Food"))
        //entries.add(PieEntry(25f,"Food"))
        //entries.add(PieEntry(15f,"Food"))
        //entries.add(PieEntry(10f,"Food"))

        for (i in 0..7) {
            entries.add(PieEntry(FilterActivity.graphDetails[i].id.toFloat(), FilterActivity.graphDetails[i].type))
        }
        val dataSet = PieDataSet(entries, "Tourism")
        //val colors = ArrayList<Int>()
        val colors = mutableListOf<Int>(
            Color.GRAY, Color.GREEN,
            Color.CYAN, Color.YELLOW,
            Color.RED, Color.BLUE,
            Color.MAGENTA, Color.DKGRAY
        )

        dataSet.sliceSpace = 2f

        dataSet.colors = colors //ColorTemplate.VORDIPLOM_COLORS
        val data = PieData(dataSet)

        chart.data = data

        chart.invalidate()

    }
}
