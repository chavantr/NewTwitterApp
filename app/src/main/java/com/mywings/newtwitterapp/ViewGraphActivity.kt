package com.mywings.newtwitterapp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.mywings.newtwitterapp.algorithm.RemoveStopWords
import kotlinx.android.synthetic.main.activity_view_graph.*


class ViewGraphActivity : AppCompatActivity() {

    private var hotel: Int = 0
    private var historicalPlacess: Int = 0
    private var beach: Int = 0
    private var waterParks: Int = 0
    private var rooms: Int = 0
    private var price: Int = 0
    private var staff: Int = 0
    private var service: Int = 0
    private var food: Int = 0
    private var flag: Boolean? = false

    private var services = arrayListOf(
        "Room",
        "Price", "Staff", "Service",
        "Food", "Hotel",
        "Historical places", "Beach", "Water park"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_graph)
        chart.setUsePercentValues(true)
        chart.setExtraOffsets(5f, 10f, 5f, 10f)
        chart.description.isEnabled = false

        flag = intent?.extras?.getBoolean("flag")

        val removeStopWords = RemoveStopWords()

        val entries = ArrayList<PieEntry>()
        val colors = mutableListOf<Int>(
            Color.GRAY, Color.GREEN,
            Color.CYAN, Color.YELLOW,
            Color.RED, Color.BLUE,
            Color.MAGENTA, Color.DKGRAY
        )

        val newColors = ArrayList<Int>()

        var i = 0
        for (node in LatestFilter.final) {
            if (node.aspectName == intent.extras?.getString("upper")) {
                if (flag!!) {
                    newColors.add(colors[i])
                    entries.add(PieEntry(node.aspectPositive.toFloat(), node.aspectList))
                } else {
                    newColors.add(colors[i])
                    entries.add(PieEntry(node.aspectNegative.toFloat(), node.aspectList))
                }
                i += 1
            }
        }

        /* entries.add(PieEntry(food.toFloat(), ""))
         entries.add(PieEntry(rooms.toFloat(), ""))
         entries.add(PieEntry(price.toFloat(), ""))
         entries.add(PieEntry(historicalPlacess.toFloat(), ""))
         entries.add(PieEntry(staff.toFloat(), ""))
         entries.add(PieEntry(service.toFloat(), ""))
         entries.add(PieEntry(beach.toFloat(), ""))
         entries.add(PieEntry(waterParks.toFloat(), ""))
         entries.add(PieEntry(hotel.toFloat(), ""))*/

        val dataSet = PieDataSet(entries, "Tourism")
        //val colors = ArrayList<Int>()


        dataSet.sliceSpace = 2f

        dataSet.colors = newColors //ColorTemplate.VORDIPLOM_COLORS
        val data = PieData(dataSet)

        chart.data = data

        chart.invalidate()

    }
}
