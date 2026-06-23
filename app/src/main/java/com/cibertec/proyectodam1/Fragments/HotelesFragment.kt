package com.cibertec.proyectodam1.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.proyectodam1.Adapters.HotelAdapter
import com.cibertec.proyectodam1.Models.Hotel
import com.cibertec.proyectodam1.R
import com.cibertec.proyectodam1.data.HotelApiClient
import com.cibertec.proyectodam1.db.ConexionDB
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HotelesFragment : Fragment() {

    private lateinit var rvHotelesFrag: RecyclerView
    private lateinit var hotelAdapter: HotelAdapter
    private lateinit var sbHotel: SearchBar
    private lateinit var svHotel: SearchView

    // Lista para mantener el control de los datos (ya sean de API o DB)
    private var listaHotelesCompleta = mutableListOf<Hotel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_hoteles, container, false)

        rvHotelesFrag = view.findViewById(R.id.rvHotelesFrag)
        sbHotel = view.findViewById(R.id.sbHotel)
        svHotel = view.findViewById(R.id.svHotel)

        svHotel.editText.setTextColor(android.graphics.Color.BLACK)
        svHotel.editText.setHintTextColor(android.graphics.Color.GRAY)

        rvHotelesFrag.layoutManager = LinearLayoutManager(requireContext())

        // 1. Inicializamos el adaptador vacío inicialmente
        hotelAdapter = HotelAdapter(listaHotelesCompleta, requireActivity()) { hotelSeleccionado ->
            val fragmento = DetalleFragment()
            val args = Bundle().apply {
                putInt("id", hotelSeleccionado)
            }
            fragmento.arguments = args
            parentFragmentManager.beginTransaction()
                .replace(R.id.fcvContenedorLista, fragmento)
                .addToBackStack(null)
                .commit()
        }
        rvHotelesFrag.adapter = hotelAdapter

        // 2. Cargamos los datos
        cargarDatos()

        // 3. Configuración del buscador
        svHotel.setupWithSearchBar(sbHotel)
        svHotel.editText.setOnEditorActionListener { _, _, _ ->
            val texto = svHotel.text.toString()
            val listaFiltrada = listaHotelesCompleta.filter { hotel ->
                hotel.nombre.contains(texto, ignoreCase = true) || hotel.ciudad.contains(texto, ignoreCase = true)
            }
            hotelAdapter.actualizarLista(listaFiltrada)
            svHotel.hide()
            true
        }

        return view
    }

    private fun cargarDatos() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // Intentamos cargar desde la API
                val hotelesApi = withContext(Dispatchers.IO) {
                    HotelApiClient.apiService.getHoteles()
                }
                listaHotelesCompleta.clear()
                listaHotelesCompleta.addAll(hotelesApi)
                hotelAdapter.actualizarLista(listaHotelesCompleta)
            } catch (e: Exception) {
                // Si falla la API, cargamos desde ConexionDB como respaldo
                Toast.makeText(requireContext(), "Cargando desde base de datos local...", Toast.LENGTH_SHORT).show()
                cargarDesdeDB()
            }
        }
    }

    private fun cargarDesdeDB() {
        val db = ConexionDB(requireContext())
        val cursor = db.obtenerHoteles()
        listaHotelesCompleta.clear()

        if (cursor.moveToFirst()) {
            do {
                listaHotelesCompleta.add(
                    Hotel(
                        cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3), cursor.getDouble(4), cursor.getString(5)
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        hotelAdapter.actualizarLista(listaHotelesCompleta)
    }
}