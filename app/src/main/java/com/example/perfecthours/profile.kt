package com.example.perfecthours

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.perfecthours.adapter.TaskListAdapter
import com.example.perfecthours.database.DatabaseHelper
import com.example.perfecthours.databinding.FragmentProfileBinding
import com.example.perfecthours.model.TaskListModel
import com.google.firebase.auth.FirebaseAuth
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.util.*
import kotlin.collections.ArrayList
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class profile : Fragment() {

    companion object {
        fun newInstance() = Fragment()
    }

    private lateinit var mAuth: FirebaseAuth
    private var fragbinding : FragmentProfileBinding? = null
    lateinit var lineGraphView: GraphView
    var date: String = ""
    var dbHandler : DatabaseHelper?= null
    var taskList: List<TaskListModel> = ArrayList<TaskListModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragbinding = FragmentProfileBinding.inflate(inflater, container, false)
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.getCurrentUser()
        fragbinding!!.name.text=currentUser?.displayName
        fragbinding!!.email.text=currentUser?.email
        Glide.with(this).load(currentUser?.photoUrl).into(fragbinding!!.profileimage);
        fragbinding!!.signoutbtn.setOnClickListener {
            mAuth.signOut()
            requireActivity().run{
                startActivity(Intent(this, signup::class.java))
                finish()
            }
        }

        lineGraphView = fragbinding!!.graphview

        var current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        date = current.format(formatter).toString()

        Log.d("Date", date)
        var productive: MutableList<Int> = mutableListOf()
        dbHandler = DatabaseHelper(requireContext())
        for (i in 1..8){
            current = LocalDateTime.now().minusDays(i.toLong())
            date = current.format(formatter).toString()
            Log.d("Date1", date)
            taskList = dbHandler!!.getTask(date)
            productive.add(taskList.size)

        }


        val series: LineGraphSeries<DataPoint> = LineGraphSeries(
            arrayOf(
                DataPoint(0.0, productive[7].toDouble()),
                DataPoint(1.0, productive[6].toDouble()),
                DataPoint(2.0, productive[5].toDouble()),
                DataPoint(3.0, productive[4].toDouble()),
                DataPoint(4.0, productive[3].toDouble()),
                DataPoint(5.0, productive[2].toDouble()),
                DataPoint(6.0, productive[1].toDouble()),
                DataPoint(7.0, productive[0].toDouble())
            )
        )
        lineGraphView.gridLabelRenderer.isVerticalLabelsVisible = false
        lineGraphView.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.NONE;
        series.thickness = 10
        series.isDrawDataPoints = true
        series.color = R.color.black
        series.isDrawBackground = true;
        series.color = Color.argb(255, 255, 60, 60);
        series.backgroundColor = Color.argb(100, 204, 119, 119);
        lineGraphView.gridLabelRenderer.numHorizontalLabels = 7
        lineGraphView.viewport.setMinX(1.0)
        lineGraphView.viewport.setMaxX(7.0)
        lineGraphView.viewport.setMinY(0.0)
        lineGraphView.viewport.setMaxY(10.0)

        lineGraphView.viewport.isYAxisBoundsManual = true
        lineGraphView.viewport.isXAxisBoundsManual = true

        lineGraphView.addSeries(series)

        return fragbinding!!.root
    }

}