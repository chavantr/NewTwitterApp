package com.mywings.newtwitterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSearch.setOnClickListener {
            if (!txtSearch.text.toString().isNullOrEmpty()) {
                val intent = Intent(this@MainActivity, FilterActivity::class.java)
                intent.putExtra("keyword", txtSearch.text.toString())
                startActivity(intent)
            } else {
                Toast.makeText(this@MainActivity, "Keyword should not empty", Toast.LENGTH_LONG).show()
            }
        }
    }
}
