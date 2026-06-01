package com.cibertec.proyectodam1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class HotelesActivity : AppCompatActivity() {

    private lateinit var bnvMenu : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hoteles)

        bnvMenu = findViewById<BottomNavigationView>(R.id.bnvMenu)

        bnvMenu.selectedItemId = R.id.Hoteles

        bnvMenu.setOnItemSelectedListener {  item ->
            when(item.itemId){
                R.id.Hoteles ->{
                    true
                }
                R.id.Inicio ->{
                    cambioActivity(InicioActivity::class.java)
                    true
                }
                R.id.Perfil ->{
                    cambioActivity(PerfilActivity::class.java)
                    true
                }
                else -> false
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dlHoteles)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun cambioActivity(activityDestino : Class<out Activity>){
        val intent = Intent(this,activityDestino)
        startActivity(intent)
    }
}