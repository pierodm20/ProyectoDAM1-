package com.cibertec.proyectodam1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.proyectodam1.Adapters.HotelAdapter
import com.cibertec.proyectodam1.Entitys.Hotel
import com.google.android.material.bottomnavigation.BottomNavigationView

class HotelesActivity : AppCompatActivity() {
    private lateinit var bnvMenu : BottomNavigationView
    private lateinit var rvHoteles: RecyclerView
    private lateinit var hotelAdapter: HotelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hoteles)

        bnvMenu = findViewById<BottomNavigationView>(R.id.bnvMenu)
        rvHoteles = findViewById<RecyclerView>(R.id.rvHoteles)


        bnvMenu.selectedItemId = R.id.Hoteles

        bnvMenu.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.Hoteles -> {
                    // SE CONECTA LA LISTA
                    cambioActivity(ListaActivity::class.java)
                    true
                }
                R.id.Inicio -> {
                    cambioActivity(InicioActivity::class.java)
                    true
                }
                R.id.Perfil -> {
                    cambioActivity(PerfilActivity::class.java)
                    true
                }
                else -> false
            }
        }

        var hoteles = listOf(
            Hotel(1, "Hotel Alcazar", 4, 199.99, ""),
            Hotel(2, "Hotel Cuzco", 2, 599.99, ""),
            Hotel(3, "Hotel Tarapoto", 10, 209.99, ""),
            Hotel(4, "Hotel Huaral", 3, 99.99, ""),
        )

        hotelAdapter = HotelAdapter(hoteles, this)
        rvHoteles.layoutManager = LinearLayoutManager(this)
        rvHoteles.adapter = hotelAdapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dlHoteles)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun cambioActivity(activityDestino : Class<out Activity>){
        val intent = Intent(this, activityDestino)
        startActivity(intent)
    }
}