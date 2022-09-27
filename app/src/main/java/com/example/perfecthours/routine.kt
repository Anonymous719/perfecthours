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

class routine : Fragment(),CalendarAdapter.OnItemListener {

    companion object {
        fun newInstance() = Fragment()
    }

    private var fragbinding : FragmentRoutineBinding? = null
    private var selectedDate : LocalDate? = null
    private lateinit var daysInMonth: ArrayList<String>
    private lateinit var calendarAdapter: CalendarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragbinding = FragmentRoutineBinding.inflate(inflater, container, false)
        selectedDate = LocalDate.now()
        setMonthView()

        fragbinding!!.prevmonth.setOnClickListener(){
            selectedDate = selectedDate?.minusMonths(1)
            setMonthView()
        }
        fragbinding!!.nextmonth.setOnClickListener(){
            selectedDate = selectedDate?.plusMonths(1)
            setMonthView()
        }

        return fragbinding!!.root
    }

    fun setMonthView(){
        fragbinding?.yymm?.setText(monthYearFromDate(selectedDate))
        daysInMonth = daysInMonthArray (selectedDate)

        calendarAdapter = CalendarAdapter(daysInMonth, this)

        var layoutManager:RecyclerView.LayoutManager = GridLayoutManager(this.context, 7)
        fragbinding!!.calendar.setLayoutManager(layoutManager)
        fragbinding!!.calendar.setAdapter(calendarAdapter)
    }

    fun daysInMonthArray(date: LocalDate?):ArrayList<String>{
        var daysInMonthArray= ArrayList<String>()
        var yearMonth: YearMonth = YearMonth.from(date)

        var daysInMonth = yearMonth.lengthOfMonth()

        var firstOfMonth : LocalDate = selectedDate!!.withDayOfMonth(1)
        var dayOfWeek = firstOfMonth.dayOfWeek.value

        for (i in 1..42){
            if (i<dayOfWeek || i>daysInMonth+dayOfWeek){
                daysInMonthArray.add("")
            }else{
                var temp = i-dayOfWeek
                daysInMonthArray.add(temp.toString())
            }
        }
        return daysInMonthArray
    }

    fun monthYearFromDate(date: LocalDate?):String{
        val formatter:DateTimeFormatter  = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date?.format(formatter) ?: String()
    }

    override fun onItemClick(position: Int, dayText: String?) {

    }

}