package com.example.perfecthours

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.perfecthours.databinding.ActivityMainBinding
import com.example.perfecthours.databinding.ActivitySignupBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class signup : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 120
    }

    private lateinit var binding: ActivitySignupBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googlesignin: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googlesignin = GoogleSignIn.getClient(this, gso)

        mAuth=FirebaseAuth.getInstance()

        binding.signin.setOnClickListener {
            signIn()
        }
    }

    private fun signIn(){
        val signInIntent = googlesignin.signInIntent
        startActivityForResult(signInIntent,RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode:Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if(task.isSuccessful){
                try{
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("Sign in activity", "firebaseAuthWithGoogle: "+account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                }catch(e: ApiException){
                    Log.w("Sign in activity", "Google sign in failed", e)
                }
            }else{
                Log.w("Sign in activity", exception.toString())
            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this){task->
                if(task.isSuccessful){
                    Log.d("SignInActivity", "Sign in with credential: success")
                    val user = mAuth.currentUser

                    val intent = Intent(this, loggedin::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Log.w("SignInActivity", "Sign in with credential: failure", task.exception)
                }
            }
    }
}