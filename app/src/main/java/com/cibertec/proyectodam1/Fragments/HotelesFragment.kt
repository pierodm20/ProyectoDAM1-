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
import com.cibertec.proyectodam1.R
import com.cibertec.proyectodam1.db.ConexionDB
import android.content.Intent
import com.cibertec.proyectodam1.Models.Hotel
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView

class HotelesFragment : Fragment() {

    private lateinit var rvHotelesFrag: RecyclerView
    private lateinit var hotelAdapter: HotelAdapter
    private lateinit var sbHotel: SearchBar
    private lateinit var svHotel: SearchView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_hoteles, container, false)
        rvHotelesFrag = view.findViewById<RecyclerView>(R.id.rvHotelesFrag)
        sbHotel = view.findViewById<SearchBar>(R.id.sbHotel)
        svHotel = view.findViewById<SearchView>(R.id.svHotel)
        svHotel.editText.setTextColor(android.graphics.Color.BLACK)
        svHotel.editText.setHintTextColor(android.graphics.Color.GRAY)

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
        hotelAdapter = HotelAdapter(listaHoteles, requireActivity()) { hotelSeleccionado ->
            val fragmento = DetalleFragment()
            val args = Bundle().apply {
                putInt("id", hotelSeleccionado)
            }
            fragmento.arguments = args
            parentFragmentManager.beginTransaction()
                .replace(R.id.fcvContenedorLista, fragmento) // ⚠️ Revisa que este ID coincida con tu XML
                .addToBackStack(null) // Permite regresar atrás
                .commit()
        }

        rvHotelesFrag.layoutManager = LinearLayoutManager(requireContext())
        rvHotelesFrag.adapter = hotelAdapter

        svHotel.setupWithSearchBar(sbHotel)

        svHotel.editText.setOnEditorActionListener { view, id, event ->
            val texto = svHotel.text.toString()
            val listaFiltrada = listaHoteles.filter { hotel ->
                hotel.nombre.contains(texto, ignoreCase = true) || hotel.ciudad.contains(texto, ignoreCase = true)

            }
            hotelAdapter.actualizarLista(listaFiltrada)

            svHotel.hide()
            true
        }

        return view
    }
}