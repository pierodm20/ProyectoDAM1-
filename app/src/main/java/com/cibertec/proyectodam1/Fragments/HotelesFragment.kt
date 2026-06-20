package com.cibertec.proyectodam1.Fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.proyectodam1.Adapters.HotelAdapter
import com.cibertec.proyectodam1.Entitys.Hotel
import com.cibertec.proyectodam1.R
import com.cibertec.proyectodam1.db.ConexionDB
import android.content.Intent
import com.cibertec.proyectodam1.DetalleReservaActivity

class HotelesFragment : Fragment() {

    private lateinit var rvHotelesFrag: RecyclerView
    private lateinit var hotelAdapter: HotelAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_hoteles, container, false)
        rvHotelesFrag = view.findViewById<RecyclerView>(R.id.rvHotelesFrag)

        // 1. Instanciamos la base de datos
        val db = ConexionDB(requireContext())

        // 2. Obtenemos el cursor con los datos reales de la tabla HOTEL
        val cursor = db.obtenerHoteles()
        val listaHoteles = mutableListOf<Hotel>()

        // 3. Convertimos el Cursor a una lista de objetos Hotel
        if (cursor.moveToFirst()) {
            do {
                val hotel = Hotel(
                    cursor.getInt(0),      // id_hotel
                    cursor.getString(1),   // nombre
                    cursor.getString(2),   // ciudad
                    cursor.getInt(3),      // estrellas
                    cursor.getDouble(4),   // precioXnoche
                    cursor.getString(5)    // imagen
                )
                listaHoteles.add(hotel)
            } while (cursor.moveToNext())
        }
        cursor.close() // Siempre cerrar el cursor

        // 4. Inicializamos el adaptador con la lista real y la función de clic
        hotelAdapter = HotelAdapter(listaHoteles, requireActivity() as Activity) { hotelSeleccionado ->
            val intent = Intent(requireContext(), DetalleReservaActivity::class.java)
            intent.putExtra("id_hotel", hotelSeleccionado.id)
            intent.putExtra("nombre_hotel", hotelSeleccionado.nombre) // Enviamos el nombre
            startActivity(intent)
        }

        rvHotelesFrag.layoutManager = LinearLayoutManager(requireContext())
        rvHotelesFrag.adapter = hotelAdapter

        return view
    }
}