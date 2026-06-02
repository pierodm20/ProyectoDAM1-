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
            Hotel(1, "JW Marriott Hotel Lima", "Lima", 5, 250.00, "https://images.unsplash.com/photo-1566073771259-6a8506099945?w=500&auto=format&fit=crop&q=60"),
            Hotel(2, "Casa Andina Select Miraflores", "Lima", 4, 130.00, "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=500&auto=format&fit=crop&q=60"),
            Hotel(3, "Belmond Miraflores Park", "Lima", 5, 320.00, "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?w=500&auto=format&fit=crop&q=60"),
            Hotel(4, "Palacio del Inka", "Cusco", 5, 280.00, "https://images.unsplash.com/photo-1571896349842-33c89424de2d?w=500&auto=format&fit=crop&q=60"),
            Hotel(5, "Tambo del Inka Resort & Spa", "Urubamba", 5, 310.00, "https://images.unsplash.com/photo-1584132967334-10e028bd69f7?w=500&auto=format&fit=crop&q=60"),
            Hotel(6, "Arawi Miraflores Prime", "Lima", 4, 95.00, "https://images.unsplash.com/photo-1590490360182-c33d57733427?w=500&auto=format&fit=crop&q=60"),
            Hotel(7, "Hotel Costa del Sol Wyndham", "Arequipa", 4, 110.00, "https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?w=500&auto=format&fit=crop&q=60"),
            Hotel(8, "Aranwa Cusco Boutique Hotel", "Cusco", 5, 210.00, "https://images.unsplash.com/photo-1611892440504-42a792e24d32?w=500&auto=format&fit=crop&q=60"),
            Hotel(9, "Ibis Larco Miraflores", "Lima", 3, 65.00, "https://images.unsplash.com/photo-1618773928121-c32242e63f39?w=500&auto=format&fit=crop&q=60"),
            Hotel(10, "DoubleTree by Hilton", "Ica", 4, 145.00, "https://images.unsplash.com/photo-1445019980597-93fa8acb246c?w=500&auto=format&fit=crop&q=60")
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