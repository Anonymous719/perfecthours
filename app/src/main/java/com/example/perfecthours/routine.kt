package com.example.perfecthours

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perfecthours.databinding.FragmentRoutineBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class routine : Fragment() {

    companion object {
        fun newInstance() = Fragment()
    }

    private var fragbinding : FragmentRoutineBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragbinding = FragmentRoutineBinding.inflate(inflater, container, false)
        return fragbinding!!.root
    }
}