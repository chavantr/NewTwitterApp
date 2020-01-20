package com.mywings.newtwitterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_view_graph.*
import com.github.mikephil.charting.data.PieEntry
import android.R.attr.entries
import android.graphics.Color
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieData


class ViewGraphActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_graph)
        chart.setUsePercentValues(true)
        chart.setExtraOffsets(100f, 100f, 100f, 100f)
        chart.description.isEnabled = false
        chart.setHoleColor(resources.getColor(R.color.colorPrimaryDark))
        chart.setTransparentCircleColor(Color.GRAY)
        chart.setTransparentCircleAlpha(110)

        chart.rotationAngle = 0f
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = true

        chart.setEntryLabelColor(Color.WHITE)
        chart.setDrawEntryLabels(true)
        chart.setDrawRoundedSlices(true)
       // chart.background = resources.getDrawable(R.color.colorPrimaryDark)
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(5f))
        entries.add(PieEntry(15f))
        entries.add(PieEntry(5f))
        entries.add(PieEntry(25f))
        entries.add(PieEntry(5f))
        entries.add(PieEntry(25f))
        entries.add(PieEntry(15f))
        entries.add(PieEntry(10f))
        val dataSet = PieDataSet(entries, "Tourism")
        val colors = ArrayList<Int>()
        colors.add(Color.GRAY)
        colors.add(Color.GREEN)
        colors.add(Color.CYAN)
        colors.add(Color.YELLOW)
        colors.add(Color.RED)
        colors.add(Color.BLUE)
        colors.add(Color.MAGENTA)
        colors.add(Color.DKGRAY)
        //dataSet.colors = colors
        val data = PieData(dataSet)
        chart.isDrawHoleEnabled = true
        chart.holeRadius  = 45f
        chart.transparentCircleRadius = 50f
        chart.data = data

    }
}
