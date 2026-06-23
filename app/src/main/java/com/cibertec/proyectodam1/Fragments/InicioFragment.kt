package com.cibertec.proyectodam1.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.proyectodam1.Adapters.HotelAdapter
import com.cibertec.proyectodam1.Models.Usuario
import com.cibertec.proyectodam1.R
import com.cibertec.proyectodam1.data.HotelDAO
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class InicioFragment : Fragment() {
    private lateinit var rvHoteles: RecyclerView
    private lateinit var adapter: HotelAdapter
    private lateinit var tvSaludo: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)

        tvSaludo  = view.findViewById(R.id.tvSaludo)
        rvHoteles = view.findViewById(R.id.rvHoteles)

        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val uid = auth.currentUser?.uid

        if (uid != null){
            db.collection("usuarios").document(uid).get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()){
                        val usu = doc.toObject(Usuario::class.java)
                        if (usu != null){
                            tvSaludo.text = "¡Bienvenido ${usu.nombre}! \uD83D\uDC4B"
                        }
                    }else{
                        mostrarMensaje("No se encontro el nombre")
                    }
                }
                .addOnFailureListener { e ->
                    mostrarMensaje("No se puedo cargar el perfil: ${e.message}")
                }
        }else{
            mostrarMensaje("El usuario no esta logeado")
        }

        configurarRecyclerView()

        return view
    }

    private fun configurarRecyclerView() {
        val hotelesDAO = HotelDAO(requireContext())
        val hoteles = hotelesDAO.obtenerTodosLosHoteles()
        val hotelesPromo = hoteles.filter { it.estrellas == 5 }

        adapter = HotelAdapter(hotelesPromo, requireActivity(),{ idhotel ->
            mostrarMensaje("${idhotel}")
        })
        rvHoteles.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        rvHoteles.adapter = adapter
    }

    fun mostrarMensaje(mensaje: String){
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
    }



}