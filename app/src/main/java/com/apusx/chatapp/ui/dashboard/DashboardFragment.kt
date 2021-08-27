package com.apusx.chatapp.ui.dashboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.apusx.chatapp.MainActivity
import com.apusx.chatapp.R
import com.apusx.chatapp.databinding.FragmentDashboardBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
     //       textView.text = it
        })


        val emailprof: TextView = binding.emailprof
        val nameprof: TextView = binding.nameprof
        val passprof: TextView = binding.passprof

        val btnprof: Button = binding.buttonprof
        val exitprf: Button = binding.exitbtn

        val user = Firebase.auth.currentUser

        user?.let {
            for (profile in it.providerData) {
                // Id of the provider (ex: google.com)
                val providerId = profile.providerId

                // UID specific to the provider
                val uid = profile.uid

                // Name, email address, and profile photo Url
                val name = profile.displayName
                val email = profile.email
                val photoUrl = profile.photoUrl

                emailprof.text = email.toString()
                nameprof.text = name.toString()

            }

        }

        btnprof.setOnClickListener(){
            val profileUpdates = userProfileChangeRequest {
                displayName = nameprof.text.toString()
                photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
            }
            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Firebase", "User profile updated.")
                        Toast.makeText(activity,"User profile updated", Toast.LENGTH_LONG).show()
                    }
                }


            user!!.updateEmail(emailprof.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Firebase", "User email address updated.")
                        Toast.makeText(activity,"User email address updated.", Toast.LENGTH_LONG).show()
                    }
                }

            user!!.updatePassword(passprof.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Firebase", "User password updated.")
                        Toast.makeText(activity,"User password updated", Toast.LENGTH_LONG).show()
                    }
                }
        }


        exitprf.setOnClickListener(){
            Firebase.auth.signOut()
            Toast.makeText(activity,"Sign Out", Toast.LENGTH_LONG).show()
            startActivity(Intent(activity,MainActivity::class.java))
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}