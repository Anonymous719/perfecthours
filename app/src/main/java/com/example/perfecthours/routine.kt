package com.example.perfecthours

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perfecthours.adapter.TaskListAdapter
import com.example.perfecthours.database.DatabaseHelper
import com.example.perfecthours.databinding.FragmentRoutineBinding
import com.example.perfecthours.model.TaskListModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class routine : Fragment() {

    companion object {
        fun newInstance() = Fragment()
    }

    private var fragbinding : FragmentRoutineBinding? = null
    var tasklistAdapter : TaskListAdapter ?= null
    var dbHandler : DatabaseHelper ?= null
    var taskList: List<TaskListModel> = ArrayList<TaskListModel>()
    var linearLayoutManager : LinearLayoutManager ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragbinding = FragmentRoutineBinding.inflate(inflater, container, false)

        dbHandler = DatabaseHelper(requireContext())

        fetchList()
        return fragbinding!!.root
    }

    private fun fetchList(){
        taskList = dbHandler!!.getAllTasks()
        tasklistAdapter = TaskListAdapter(taskList, requireActivity().getApplicationContext())
        linearLayoutManager = LinearLayoutManager(requireActivity().getApplicationContext())
        fragbinding?.schedulerecycler?.layoutManager = linearLayoutManager
        fragbinding?.schedulerecycler?.adapter = tasklistAdapter
        tasklistAdapter?.notifyDataSetChanged()
    }

}