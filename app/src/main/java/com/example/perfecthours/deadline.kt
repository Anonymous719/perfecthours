package com.example.perfecthours

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perfecthours.adapter.TaskListAdapter
import com.example.perfecthours.database.DatabaseHelper
import com.example.perfecthours.databinding.FragmentDeadlineBinding
import com.example.perfecthours.databinding.FragmentNotificationBinding
import com.example.perfecthours.databinding.FragmentRoutineBinding
import com.example.perfecthours.model.TaskListModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class deadline : Fragment() {

    companion object {
        fun newInstance() = Fragment()
    }

    private var fragbinding : FragmentDeadlineBinding? = null
    var tasklistAdapter : TaskListAdapter?= null
    var dbHandler : DatabaseHelper?= null
    var taskList: MutableList<TaskListModel> = ArrayList<TaskListModel>()
    var linearLayoutManager : LinearLayoutManager?= null
    var date:String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragbinding = FragmentDeadlineBinding.inflate(inflater, container, false)


        fetchList()
        return fragbinding!!.root
    }

    private fun fetchList(){
        taskList.clear()
        dbHandler = DatabaseHelper(requireContext())

        var current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/d")
        date = current.format(formatter).toString()

        dbHandler = DatabaseHelper(requireContext())
        for (i in 0..8){
            current = LocalDateTime.now().plusDays(i.toLong())
            date = current.format(formatter).toString()
            Log.d("Date1", date)
            taskList.addAll(dbHandler!!.getTask(date))

        }
        tasklistAdapter = TaskListAdapter(taskList, requireActivity().getApplicationContext())
        linearLayoutManager = LinearLayoutManager(requireActivity().getApplicationContext())
        fragbinding?.taskRecyclerView?.layoutManager = linearLayoutManager
        fragbinding?.taskRecyclerView?.adapter = tasklistAdapter
        tasklistAdapter?.notifyDataSetChanged()
    }


}