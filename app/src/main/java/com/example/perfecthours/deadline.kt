package com.example.perfecthours

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perfecthours.adapter.TaskListAdapter
import com.example.perfecthours.database.DatabaseHelper
import com.example.perfecthours.databinding.FragmentDeadlineBinding
import com.example.perfecthours.databinding.FragmentRoutineBinding
import com.example.perfecthours.model.TaskListModel


class deadline : Fragment() {

    companion object {
        fun newInstance() = Fragment()
    }

    private var fragbinding : FragmentDeadlineBinding? = null
    var tasklistAdapter : TaskListAdapter?= null
    var dbHandler : DatabaseHelper?= null
    var taskList: List<TaskListModel> = ArrayList<TaskListModel>()
    var linearLayoutManager : LinearLayoutManager?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragbinding = FragmentDeadlineBinding.inflate(inflater, container, false)

        dbHandler = DatabaseHelper(requireContext())

        fetchList()
        return fragbinding!!.root
    }

    private fun fetchList(){
        taskList = dbHandler!!.getAllTasks()
        tasklistAdapter = TaskListAdapter(taskList, requireActivity().getApplicationContext())
        linearLayoutManager = LinearLayoutManager(requireActivity().getApplicationContext())
        fragbinding?.taskRecyclerView?.layoutManager = linearLayoutManager
        fragbinding?.taskRecyclerView?.adapter = tasklistAdapter
        tasklistAdapter?.notifyDataSetChanged()
    }


}