package com.cibertec.proyectodam1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.cibertec.proyectodam1.Fragments.HotelesFragment
import com.cibertec.proyectodam1.Fragments.InicioFragment
import com.cibertec.proyectodam1.Fragments.PerfilFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ListaActivity : AppCompatActivity() {

    private lateinit var bnvMenu : BottomNavigationView
    private lateinit var lvListaHoteles: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista)

        // 1. Enlazar la lista del layout
        lvListaHoteles = findViewById(R.id.lvListaHoteles)

        // 2. Datos directos de los hoteles del proyecto para mostrar en la lista
        val datosHoteles = arrayOf(
            "Hotel Melia Lima - San Isidro (5 Estrellas)",
            "Dazzler by Wyndham - Miraflores (Piscina Terraza)",
            "Sheraton Lima Hotel - Centro de Lima (Gran Confort)",
            "Hotel Selina Miraflores - Ambiente Juvenil",
            "Hilton Lima Miraflores - Hospedaje de Lujo"
        )


        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, datosHoteles)
        lvListaHoteles.adapter = adapter


        bnvMenu = findViewById<BottomNavigationView>(R.id.bnvMenu)

        bnvMenu.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.Hoteles -> {
                    cargarFragmento(HotelesFragment())
                    true
                }
                R.id.Inicio -> {
                    cargarFragmento(PerfilFragment())
                    true
                }
                R.id.Perfil -> {
                    cargarFragmento(InicioFragment())
                    true
                }
                else -> false
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dlLista)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun cargarFragmento(fragmento: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fcvContenedorLista, fragmento).commit()
    }
}