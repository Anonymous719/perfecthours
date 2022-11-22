package com.example.perfecthours

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.perfecthours.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class profile : Fragment() {

    companion object {
        fun newInstance() = Fragment()
    }

    private lateinit var mAuth: FirebaseAuth
    private var fragbinding : FragmentProfileBinding? = null

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
        return fragbinding!!.root
    }

}