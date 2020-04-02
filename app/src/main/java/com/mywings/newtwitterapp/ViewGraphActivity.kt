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

        for (node in FilterActivity.newType) {
            if (flag!!) {
                when (node.classLabel) {
                    "Room" -> {
                        rooms = rooms.plus(removeStopWords.positiveTopics(node.comment, this@ViewGraphActivity))
                    }
                    "Price" -> {
                        price = price.plus(removeStopWords.positiveTopics(node.comment, this@ViewGraphActivity))
                    }
                    "Staff" -> {
                        staff = staff.plus(removeStopWords.positiveTopics(node.comment, this@ViewGraphActivity))
                    }
                    "Service" -> {
                        service = service.plus(removeStopWords.positiveTopics(node.comment, this@ViewGraphActivity))
                    }
                    "Food" -> {
                        food = food.plus(removeStopWords.positiveTopics(node.comment, this@ViewGraphActivity))
                    }
                    "Hotel" -> {
                        hotel = hotel.plus(removeStopWords.positiveTopics(node.comment, this@ViewGraphActivity))
                    }
                    "Historical places" -> {
                        historicalPlacess =
                            historicalPlacess.plus(removeStopWords.positiveTopics(node.comment, this@ViewGraphActivity))
                    }
                    "Beach" -> {
                        beach = beach.plus(removeStopWords.positiveTopics(node.comment, this@ViewGraphActivity))
                    }
                    "Water park" -> {
                        waterParks =
                            waterParks.plus(removeStopWords.positiveTopics(node.comment, this@ViewGraphActivity))
                    }
                }
            } else {
                when (node.classLabel) {
                    "Room" -> {
                        rooms = rooms.minus(removeStopWords.positiveTopics(node.comment, this@ViewGraphActivity))
                    }
                    "Price" -> {
                        price = price.minus(removeStopWords.positiveTopics(node.comment, this@ViewGraphActivity))
                    }
                    "Staff" -> {
                        staff = staff.minus(removeStopWords.positiveTopics(node.comment, this@ViewGraphActivity))
                    }
                    "Service" -> {
                        service = service.minus(removeStopWords.positiveTopics(node.comment, this@ViewGraphActivity))
                    }
                    "Food" -> {
                        food = food.minus(removeStopWords.positiveTopics(node.comment, this@ViewGraphActivity))
                    }
                    "Hotel" -> {
                        hotel = hotel.minus(removeStopWords.positiveTopics(node.comment, this@ViewGraphActivity))
                    }
                    "Historical places" -> {
                        historicalPlacess =
                            historicalPlacess.minus(
                                removeStopWords.positiveTopics(
                                    node.comment,
                                    this@ViewGraphActivity
                                )
                            )
                    }
                    "Beach" -> {
                        beach = beach.minus(removeStopWords.positiveTopics(node.comment, this@ViewGraphActivity))
                    }
                    "Water park" -> {
                        waterParks =
                            waterParks.minus(removeStopWords.positiveTopics(node.comment, this@ViewGraphActivity))
                    }
                }
            }
        }

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(food.toFloat(), ""))
        entries.add(PieEntry(rooms.toFloat(), ""))
        entries.add(PieEntry(price.toFloat(), ""))
        entries.add(PieEntry(historicalPlacess.toFloat(), ""))
        entries.add(PieEntry(staff.toFloat(), ""))
        entries.add(PieEntry(service.toFloat(),""))
        entries.add(PieEntry(beach.toFloat(),""))
        entries.add(PieEntry(waterParks.toFloat(),""))
        entries.add(PieEntry(hotel.toFloat(),""))

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
