package com.apusx.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashScreen : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val user = Firebase.auth.currentUser
            if (user != null) {
                startActivity(Intent(this,ChatActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }

        },SPLASH_TIME_OUT)
    }
}