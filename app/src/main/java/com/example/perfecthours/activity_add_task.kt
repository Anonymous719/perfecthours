package com.example.perfecthours

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import com.example.perfecthours.database.DatabaseHelper
import com.example.perfecthours.databinding.ActivityAddTaskBinding
import com.example.perfecthours.databinding.ActivityMainBinding
import com.example.perfecthours.model.TaskListModel
import java.util.*

class activity_add_task : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var binding: ActivityAddTaskBinding

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    var dbHandler:DatabaseHelper ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pickDate()

        dbHandler = DatabaseHelper(this)

        binding.add.setOnClickListener {
            var success:Boolean = false
            var task = TaskListModel()
            task.name = binding.name.text.toString()
            task.details = binding.details.text.toString()
            task.date = "$savedYear/$savedMonth/$savedDay"
            if (savedMinute == 0){
                task.time = "$savedHour:$savedMinute"+"0"
            }else {
                task.time = "$savedHour:$savedMinute"
            }
            success = dbHandler?.addTask(task) as Boolean

            if(success){
                val i = Intent(applicationContext,loggedin::class.java)
                startActivity(i)
                finish()
            }
        }

    }

    private fun pickDate(){
        binding.selectDate.setOnClickListener {
            getDateTimeCalendar()

            DatePickerDialog(this,this,year,month,day).show()

        }
    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }



    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalendar()

        TimePickerDialog(this,this,hour,minute,true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        savedMonth++
        if (savedMinute == 0) {
            binding.selectDate.text = "$savedYear/$savedMonth/$savedDay  $savedHour:$savedMinute"+"0"
        }else{
            binding.selectDate.text = "$savedYear/$savedMonth/$savedDay  $savedHour:$savedMinute"
        }


    }
}