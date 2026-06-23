package com.cibertec.proyectodam1

import android.animation.Animator
import android.content.Intent
import android.os.Bundle

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import kotlin.jvm.java

class SplashActivity : AppCompatActivity() {
    private lateinit var lottieLogo: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        lottieLogo = findViewById<LottieAnimationView>(R.id.lottieLogo)

        lottieLogo.speed = 0.5f

        lottieLogo.addAnimatorListener(object : android.animation.Animator.AnimatorListener {
            override fun onAnimationCancel(animation: android.animation.Animator) {}
            override fun onAnimationRepeat(animation: android.animation.Animator) {}
            override fun onAnimationStart(animation: android.animation.Animator) {}
            override fun onAnimationEnd(p0: Animator) {
                val prefs = getSharedPreferences("MiAppPrefs", MODE_PRIVATE)
                val esPrimeraVez = prefs.getBoolean("esPrimeraVez", true)

                if (esPrimeraVez) {
                    startActivity(Intent(this@SplashActivity, TutorialActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                }
                finish()
            }
        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}