package com.example.perfecthours

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perfecthours.adapter.TaskListAdapter
import com.example.perfecthours.database.DatabaseHelper
import com.example.perfecthours.databinding.FragmentRoutineBinding
import com.example.perfecthours.model.TaskListModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class routine : Fragment() {

    companion object {
        fun newInstance() = Fragment()
    }

    private var fragbinding : FragmentRoutineBinding? = null


    var date: String = ""
    var tasklistAdapter : TaskListAdapter ?= null
    var dbHandler : DatabaseHelper ?= null
    var taskList: List<TaskListModel> = ArrayList<TaskListModel>()
    var linearLayoutManager : LinearLayoutManager ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragbinding = FragmentRoutineBinding.inflate(inflater, container, false)

        var current = LocalDateTime.now()

        dbHandler = DatabaseHelper(requireContext())
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        date = current.format(formatter).toString()
        fetchList(date)

        fragbinding!!.calendarView.setOnDateChangeListener { calView: CalendarView, year: Int, month: Int, dayOfMonth: Int ->

            // Create calender object with which will have system date time.
            val calender: Calendar = Calendar.getInstance()

            // Set attributes in calender object as per selected date.
            calender.set(year, month, dayOfMonth)

            // Now set calenderView with this calender object to highlight selected date on UI.
            calView.setDate(calender.timeInMillis, true, true)
            date = "$year/${month + 1}/$dayOfMonth"
            Log.d("Date",date)

            fetchList(date)

        }
        return fragbinding!!.root
    }

    private fun fetchList(date:String){
        taskList = dbHandler!!.getTask(date)
        Log.d("task",taskList.size.toString())
        tasklistAdapter = TaskListAdapter(taskList, requireActivity().getApplicationContext())
        linearLayoutManager = LinearLayoutManager(requireActivity().getApplicationContext())
        fragbinding?.schedulerecycler?.layoutManager = linearLayoutManager
        fragbinding?.schedulerecycler?.adapter = tasklistAdapter
        tasklistAdapter?.notifyDataSetChanged()
    }

}