package com.example.perfecthours

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.perfecthours.databinding.ActivityLoggedinBinding
import com.example.perfecthours.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class loggedin : AppCompatActivity() {

    private val routinefragment = routine()
    private val deadlinefragment = deadline()
    private val profilefragment = profile()
    private val notificationfragment = notification()
    private lateinit var binding: ActivityLoggedinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoggedinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomnavigation.background=null
        binding.bottomnavigation.menu.getItem(2).isEnabled=false

        replaceFragment(routinefragment)

        binding!!.btnAdd.setOnClickListener {
            val i = Intent(this, activity_add_task::class.java)
            startActivity(i)
        }

        binding.bottomnavigation.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.routine -> replaceFragment(routinefragment)
                R.id.deadline -> replaceFragment(deadlinefragment)
                R.id.profile -> replaceFragment(profilefragment)
                R.id.notifications -> replaceFragment(notificationfragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment!=null){
            val transaction =supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,fragment)
            transaction.commit()
        }
    }
}