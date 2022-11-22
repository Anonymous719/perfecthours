package com.example.perfecthours

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.perfecthours.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        val user=mAuth.currentUser

        Handler().postDelayed({
            if(user != null){
                val loggedinIntent = Intent(this, loggedin::class.java)
                startActivity(loggedinIntent)
                finish()
            }else{
                val signupIntent=Intent(this, signup::class.java)
                startActivity(signupIntent)
                finish()
            }
        },1500)

    }


}
