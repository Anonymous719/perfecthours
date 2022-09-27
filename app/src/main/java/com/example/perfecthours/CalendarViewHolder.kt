package com.example.perfecthours

import android.view.View
import com.example.perfecthours.CalendarAdapter.OnItemListener
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.example.perfecthours.R

class CalendarViewHolder(itemView: View, onItemListener: OnItemListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    @JvmField
    val dayofmonth: TextView
    private val onItemListener: OnItemListener
    override fun onClick(v: View) {
        onItemListener.onItemClick(adapterPosition, dayofmonth.text as String)
    }

    init {
        dayofmonth = itemView.findViewById(R.id.celldaytext)
        this.onItemListener = onItemListener
        itemView.setOnClickListener(this)
    }
}