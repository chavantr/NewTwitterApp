package com.mywings.newtwitterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import com.mywings.newtwitterapp.model.FetchAspect
import com.mywings.newtwitterapp.process.FetchValueAsync
import com.mywings.newtwitterapp.process.OnFetchListener
import com.mywings.newtwitterapp.process.ProgressDialogUtil
import kotlinx.android.synthetic.main.activity_latest_filter.*

class LatestFilter : AppCompatActivity(), OnFetchListener {

    private lateinit var progressDialogUtil: ProgressDialogUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_filter)
        progressDialogUtil = ProgressDialogUtil(this)
        init()
        btnHotel.setOnClickListener {
            val upperTag = "Hotel"
            var lowerTag = ""
            val popupMenu = PopupMenu(this@LatestFilter, it)
            popupMenu.menuInflater.inflate(R.menu.menu_hotel, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                lowerTag = item.title.toString()
                when (item.itemId) {
                    R.id.menu_room -> {
                        startFewIntent(upperTag, lowerTag)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.menu_price -> {
                        startFewIntent(upperTag, lowerTag)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.menu_staff -> {
                        startFewIntent(upperTag, lowerTag)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.menu_service -> {
                        startFewIntent(upperTag, lowerTag)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.menu_food -> {
                        startFewIntent(upperTag, lowerTag)
                        return@setOnMenuItemClickListener true
                    }
                    else -> return@setOnMenuItemClickListener false
                }
            }
            popupMenu.show()
        }

        btnHistoricalPlaces.setOnClickListener {
            val upperTag = "Historical Places"
            var lowerTag = ""
            val popupMenu = PopupMenu(this@LatestFilter, it)
            popupMenu.menuInflater.inflate(R.menu.menu_historical_places, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                lowerTag = item.title.toString()
                when (item.itemId) {
                    R.id.menu_cleanness -> {
                        startFewIntent(upperTag, lowerTag)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.menu_price -> {
                        startFewIntent(upperTag, lowerTag)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.menu_safety -> {
                        startFewIntent(upperTag, lowerTag)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.menu_food -> {
                        startFewIntent(upperTag, lowerTag)
                        return@setOnMenuItemClickListener true
                    }
                    else -> return@setOnMenuItemClickListener false
                }
            }
            popupMenu.show()
        }

        btnBeach.setOnClickListener {
            val upperTag = "Beach"
            var lowerTag = ""
            val popupMenu = PopupMenu(this@LatestFilter, it)
            popupMenu.menuInflater.inflate(R.menu.menu_beach, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                lowerTag = item.title.toString()
                when (item.itemId) {
                    R.id.menu_cleanness -> {
                        startFewIntent(upperTag, lowerTag)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.menu_price -> {
                        startFewIntent(upperTag, lowerTag)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.menu_food -> {
                        startFewIntent(upperTag, lowerTag)
                        return@setOnMenuItemClickListener true
                    }
                    else -> return@setOnMenuItemClickListener false
                }
            }
            popupMenu.show()
        }

        btnWaterParks.setOnClickListener {
            val upperTag = "Water Parks"
            var lowerTag = ""
            val popupMenu = PopupMenu(this@LatestFilter, it)
            popupMenu.menuInflater.inflate(R.menu.menu_water_parks, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                lowerTag = item.title.toString()
                when (item.itemId) {
                    R.id.menu_cleanness -> {
                        startFewIntent(upperTag, lowerTag)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.menu_price -> {
                        startFewIntent(upperTag, lowerTag)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.menu_room -> {
                        startFewIntent(upperTag, lowerTag)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.menu_food -> {
                        startFewIntent(upperTag, lowerTag)
                        return@setOnMenuItemClickListener true
                    }
                    else -> return@setOnMenuItemClickListener false
                }
            }
            popupMenu.show()
        }

        btnAllAspects.setOnClickListener {
            val popupMenu = PopupMenu(this@LatestFilter, it)
            popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_hotel -> {
                        positiveNegativeMenu(item.title.toString())
                        return@setOnMenuItemClickListener true
                    }
                    R.id.menu_places -> {
                        positiveNegativeMenu(item.title.toString())
                        return@setOnMenuItemClickListener true
                    }
                    R.id.menu_beach -> {
                        positiveNegativeMenu(item.title.toString())
                        return@setOnMenuItemClickListener true
                    }
                    R.id.menu_water_parks -> {
                        positiveNegativeMenu(item.title.toString())
                        return@setOnMenuItemClickListener true
                    }
                    else -> return@setOnMenuItemClickListener false
                }
            }
            popupMenu.show()
        }
    }

    private fun init() {
        progressDialogUtil.show()
        val fetchAspect = FetchValueAsync()
        fetchAspect.setOnFetchListener(this)
    }

    private fun startFewIntent(upper: String, lower: String) {
        val intent = Intent(this@LatestFilter, ResultServiceActivity::class.java)
        intent.putExtra("upper", upper)
        intent.putExtra("lower", lower)
        startActivity(intent)
    }

    private fun startIntent(flag: Boolean, upper: String) {
        val intent = Intent(this@LatestFilter, ViewGraphActivity::class.java)
        intent.putExtra("flag", flag)
        intent.putExtra("upper", upper)
        startActivity(intent)
    }

    private fun positiveNegativeMenu(upper: String) {
        val popupMenu = PopupMenu(this@LatestFilter, btnAllAspects)
        popupMenu.menuInflater.inflate(R.menu.menu_positive_negative, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_positive -> {
                    val intent = Intent(this@LatestFilter, ViewGraphActivity::class.java)
                    intent.putExtra("flag", true)
                    intent.putExtra("upper", upper)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    val intent = Intent(this@LatestFilter, ViewGraphActivity::class.java)
                    intent.putExtra("flag", false)
                    intent.putExtra("upper", upper)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
            }
        }
        popupMenu.show()
    }

    override fun onFetchSuccess(result: List<FetchAspect>?) {
        progressDialogUtil.hide()
        final = result!!
    }

    companion object {
        lateinit var final: List<FetchAspect>
    }
}
