package com.cibertec.proyectodam1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class PerfilActivity : AppCompatActivity() {
    private lateinit var bnvMenu : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)

        bnvMenu = findViewById<BottomNavigationView>(R.id.bnvMenu)
        //Marca el ID del Icono
        bnvMenu.selectedItemId = R.id.Perfil
        bnvMenu.setOnItemSelectedListener {  item ->
            when(item.itemId){
                R.id.Perfil ->{
                    true
                }
                R.id.Hoteles ->{
                    cambioActivity(HotelesActivity::class.java)
                    true
                }
                R.id.Inicio ->{
                    cambioActivity(InicioActivity::class.java)
                    true
                }
                else -> false
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dlPerfil)) { v, insets ->
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