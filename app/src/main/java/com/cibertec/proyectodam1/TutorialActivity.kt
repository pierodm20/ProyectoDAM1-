package com.cibertec.proyectodam1

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.transition.Visibility
import androidx.viewpager2.widget.ViewPager2
import com.cibertec.proyectodam1.Adapters.TutorialAdapter
import com.google.android.material.button.MaterialButton
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class TutorialActivity : AppCompatActivity() {
    private lateinit var viewPagerTutorial: ViewPager2
    private lateinit var dotsIndicator: DotsIndicator
    private lateinit var btnComenzar : MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tutorial)
        viewPagerTutorial = findViewById<ViewPager2>(R.id.viewPagerTutorial)
        dotsIndicator = findViewById<DotsIndicator>(R.id.dotsIndicator)
        btnComenzar = findViewById<MaterialButton>(R.id.btnComenzar)

        val titulo = listOf("Bienvinido a HotelMatch", "Precio Imperdibles", "En cualquier parte del Perú")
        val descripciones = listOf("Conoceras una experiencia interactiva para tus viajes", "Hoteles de toda parte del mundo al mejor precio", "Disfruta de tus viajes en los mejores lugares")

        viewPagerTutorial.adapter = TutorialAdapter(titulo, descripciones)
        dotsIndicator.attachTo(viewPagerTutorial)
        viewPagerTutorial.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == descripciones.size - 1){
                    btnComenzar.visibility = View.VISIBLE
                }else{
                    btnComenzar.visibility = View.GONE
                }
            }
        })

        btnComenzar.setOnClickListener {
            marcarTutorialComoVisto()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun marcarTutorialComoVisto() {
        val prefs = getSharedPreferences("MiAppPrefs", MODE_PRIVATE)
        prefs.edit().putBoolean("esPrimeraVez", false).apply()
    }
}