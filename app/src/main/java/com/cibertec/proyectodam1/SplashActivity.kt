package com.cibertec.proyectodam1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    private var timer: CountDownTimer? = null
    private lateinit var ivLogo: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ivLogo = findViewById<ImageView>(R.id.ivLogo)

        ivLogo.animate().rotationBy(360f).setDuration(2000).start()

        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            val prefs = getSharedPreferences("MiAppPrefs", MODE_PRIVATE)
            val esPrimeraVez = prefs.getBoolean("esPrimeraVez", true)

            if (esPrimeraVez) {
                startActivity(Intent(this, TutorialActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 2000)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}