package com.apusx.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.apusx.chatapp.databinding.ActivityMainBinding
import com.apusx.chatapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUP : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private lateinit var auth: FirebaseAuth

    val TAG = "TEST"

    fun String.isValidEmail(): Boolean
            = this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = Firebase.auth


        binding.signupsig.setOnClickListener() {

            if (binding.name.text.toString().isEmpty())
                Toast.makeText(this, "Name Invalid", Toast.LENGTH_LONG).show()
            else
                if (binding.emailsig.text.toString().isEmpty())
                    Toast.makeText(this, "Email Invalid", Toast.LENGTH_LONG).show()
                else
                    if (binding.passwordsig.text.toString().isEmpty())
                        Toast.makeText(this, "Pass Invalid", Toast.LENGTH_LONG).show()
                    else
                        if (binding.passwordconfsig.text.toString().isEmpty())
                            Toast.makeText(this, "ConfPass Invalid", Toast.LENGTH_LONG).show()
                        else
                            if (!binding.passwordsig.text.toString()
                                    .equals(binding.passwordconfsig.text.toString())
                            )
                                Toast.makeText(this, "ConfPass Wrong", Toast.LENGTH_LONG).show()
                            else {
                                auth.createUserWithEmailAndPassword(
                                    binding.emailsig.text.toString(),
                                    binding.passwordsig.text.toString()
                                )
                                    .addOnCompleteListener(this) { task ->
                                        if (task.isSuccessful) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "createUserWithEmail:success")
                                            val user = auth.currentUser
                                            Toast.makeText(this,user?.uid.toString(),Toast.LENGTH_LONG).show()
                                            startActivity(Intent(this,ChatActivity::class.java))

                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(
                                                TAG,
                                                "createUserWithEmail:failure",
                                                task.exception
                                            )
                                            Toast.makeText(
                                                baseContext, "Authentication failed.",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                        }
                                    }
                            }
        }


        binding.cancel.setOnClickListener() {
            finish()
        }


    }


}