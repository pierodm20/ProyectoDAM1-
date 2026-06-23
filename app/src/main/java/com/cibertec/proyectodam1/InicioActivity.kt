package com.cibertec.proyectodam1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.cibertec.proyectodam1.Fragments.HotelesFragment
import com.cibertec.proyectodam1.Fragments.InicioFragment
import com.cibertec.proyectodam1.Fragments.PerfilFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class InicioActivity : AppCompatActivity() {
    private lateinit var bnvMenu : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)

        bnvMenu = findViewById<BottomNavigationView>(R.id.bnvMenu)
        if (savedInstanceState == null){
            cargarFragmento(InicioFragment())
            bnvMenu.selectedItemId = R.id.Inicio
        }

        bnvMenu.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.Hoteles -> {
                    // SE CONECTA LA LISTA
                    cargarFragmento(HotelesFragment())
                    true
                }
                R.id.Inicio -> {
                    cargarFragmento(InicioFragment())

                    true
                }
                R.id.Perfil -> {
                    cargarFragmento(PerfilFragment())
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
    private fun cargarFragmento(fragmento: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fcvContenedorLista, fragmento).commit()
    }
}