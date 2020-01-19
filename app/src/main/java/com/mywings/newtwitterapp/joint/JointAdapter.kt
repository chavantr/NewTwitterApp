package com.mywings.newtwitterapp.joint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mywings.newtwitterapp.R
import kotlinx.android.synthetic.main.layout_row.view.*

class JointAdapter(var lstResult: ArrayList<String>) : RecyclerView.Adapter<JointAdapter.FinalResultAdapter>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinalResultAdapter {
        return FinalResultAdapter(LayoutInflater.from(parent.context).inflate(R.layout.layout_row, parent, false))
    }

    override fun getItemCount(): Int = lstResult.size

    override fun onBindViewHolder(holder: FinalResultAdapter, position: Int) {
        holder.lblName.text = lstResult[position]
    }


    inner class FinalResultAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lblName = itemView.lblName
    }
}