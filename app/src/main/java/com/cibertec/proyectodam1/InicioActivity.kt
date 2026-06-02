package com.cibertec.proyectodam1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

class InicioActivity : AppCompatActivity() {


    private lateinit var bnvMenu : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)

        bnvMenu = findViewById<BottomNavigationView>(R.id.bnvMenu)
        //Marca el ID del Icono
        bnvMenu.selectedItemId = R.id.Inicio

        bnvMenu.setOnItemSelectedListener {  item ->
            when(item.itemId){
                R.id.Inicio ->{
                    true
                }
                R.id.Hoteles ->{
                    cambioActivity(HotelesActivity::class.java)
                    true
                }
                R.id.Perfil ->{
                    cambioActivity(PerfilActivity::class.java)
                    true
                }
                else -> false
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dlInicio)) { v, insets ->
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