package com.example.perfecthours.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.perfecthours.model.TaskListModel
import com.google.android.gms.tasks.Task
import java.util.Date

class DatabaseHelper(context: Context) :SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){

    companion object {
        private val DB_NAME = "schedule"
        private val DB_VERSION = 1
        private val TABLE_NAME = "schedule"
        private val ID = "id"
        private val TASK_NAME = "taskname"
        private val TASK_DETAILS = "taskdetails"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $TASK_NAME TEXT, $TASK_DETAILS TEXT);"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    @SuppressLint("Range")
    fun getAllTasks(): List<TaskListModel> {
        val tasklist = ArrayList<TaskListModel>()
        val dab = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = dab.rawQuery(selectQuery,null)
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    val tasks = TaskListModel()
                    tasks.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    tasks.name = cursor.getString(cursor.getColumnIndex(TASK_NAME))
                    tasks.details = cursor.getString(cursor.getColumnIndex(TASK_DETAILS))
                    tasklist.add(tasks)
                }while(cursor.moveToNext())
            }
        }
        cursor.close()
        return tasklist
    }

    fun addTask(task: TaskListModel):Boolean {
        val dab = this.writableDatabase
        val values = ContentValues()
        values.put(TASK_NAME,task.name)
        values.put(TASK_DETAILS,task.details)
        val _success = dab.insert(TABLE_NAME,null,values)
        dab.close()
        return (Integer.parseInt("$_success")!=-1)
    }

    @SuppressLint("Range")
    fun getTask(_id:Int):TaskListModel{
        val tasks = TaskListModel()
        val dab = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE ID = $_id"
        val cursor = dab.rawQuery(selectQuery,null)

        cursor?.moveToFirst()
        tasks.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        tasks.name = cursor.getString(cursor.getColumnIndex(TASK_NAME))
        tasks.details = cursor.getString(cursor.getColumnIndex(TASK_DETAILS))
        cursor.close()
        return tasks
    }

    fun deleteTask(_id:Int):Boolean{
        val dab = writableDatabase
        val _success = dab.delete(TABLE_NAME, ID + "=?", arrayOf(_id.toString())).toLong()
        dab.close()
        return (Integer.parseInt("$_success")!=-1)
    }

    fun updateTask(task: TaskListModel):Boolean{
        val dab = writableDatabase
        val values = ContentValues()
        values.put(TASK_NAME,task.name)
        values.put(TASK_DETAILS,task.details)
        val _success = dab.update(TABLE_NAME, values, ID+"=?", arrayOf(task.id.toString())).toLong()
        dab.close()
        return (Integer.parseInt("$_success")!=-1)
    }
}