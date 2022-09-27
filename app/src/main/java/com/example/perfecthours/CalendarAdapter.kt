package com.example.perfecthours

import android.util.Log
import com.example.perfecthours.CalendarAdapter.OnItemListener
import androidx.recyclerview.widget.RecyclerView
import com.example.perfecthours.CalendarViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.perfecthours.R
import java.util.ArrayList

class CalendarAdapter(
    private val daysofmonth: ArrayList<String>,
    private val onItemListener: OnItemListener
) : RecyclerView.Adapter<CalendarViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.166666).toInt()
        return CalendarViewHolder(view, onItemListener)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayofmonth.text = daysofmonth[position]
        Log.d("Test1","Hello")
    }

    override fun getItemCount(): Int {
        return daysofmonth.size
    }

    interface OnItemListener {
        fun onItemClick(position: Int, dayText: String?)
    }
}