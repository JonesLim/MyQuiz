package com.jones.myquiz.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.jones.myquiz.MainActivity
import com.jones.myquiz.R
import com.jones.myquiz.core.service.AuthService
import com.jones.myquiz.data.repo.UserRepo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var authService: AuthService

    @Inject
    lateinit var userRepo: UserRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        checkUserAuthentication()
    }

    private fun checkUserAuthentication() {
        CoroutineScope(Dispatchers.IO).launch {
            val currentUser = authService.getCurrentUser()
            val destination = if (currentUser != null) {
                try {
                    val role = userRepo.getUserRole(currentUser.uid)
                    when (role) {
                        "Teacher" -> R.id.teacherDashFragment
                        "Student" -> R.id.studentDashFragment
                        else -> R.id.loginFragment
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    R.id.loginFragment
                }
            } else {
                R.id.loginFragment
            }

            runOnUiThread {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this@SplashActivity, MainActivity::class.java).apply {
                        putExtra("destination", destination)
                    }
                    startActivity(intent)
                    finish()
                }, 2000L)
            }
        }
    }
}
