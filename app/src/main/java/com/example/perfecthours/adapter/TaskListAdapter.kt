package com.example.perfecthours.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.perfecthours.R
import com.example.perfecthours.model.TaskListModel

class TaskListAdapter(taskList: List<TaskListModel>, internal val context: Context): RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    internal var taskList: List<TaskListModel> = ArrayList()
    init {
        this.taskList = taskList
    }

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var name: TextView = view.findViewById(R.id.name)
        var details: TextView = view.findViewById(R.id.details)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_task_list, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.name.text = task.name
        holder.details.text = task.details
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

}