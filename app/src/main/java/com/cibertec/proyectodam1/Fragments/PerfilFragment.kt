package com.cibertec.proyectodam1.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.cibertec.proyectodam1.LoginActivity
import com.cibertec.proyectodam1.R

class PerfilFragment : Fragment() {
    private lateinit var txtUsuarioPerfil: TextView
    private lateinit var optionDatosPersonales: LinearLayout
    private lateinit var optionConfiguraciones: LinearLayout
    private lateinit var optionCerrarSesion: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)
        txtUsuarioPerfil = view.findViewById<TextView>(R.id.txtUsuarioPerfil)
        optionDatosPersonales = view.findViewById<LinearLayout>(R.id.optionDatosPersonales)
        optionConfiguraciones = view.findViewById<LinearLayout>(R.id.optionConfiguraciones)
        optionCerrarSesion = view.findViewById<LinearLayout>(R.id.optionCerrarSesion)

        txtUsuarioPerfil.text = "Piero Donayre"
        optionDatosPersonales.setOnClickListener {
            Toast.makeText(requireContext(), "Abriendo Datos personales", Toast.LENGTH_SHORT).show()
        }
        optionConfiguraciones.setOnClickListener {
            Toast.makeText(requireContext(), "Abriendo Configuraciones", Toast.LENGTH_SHORT).show()
        }
        optionCerrarSesion.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(requireContext(), "Cerrando sesión", Toast.LENGTH_SHORT).show()
            requireActivity().finish()
        }
        // Inflate the layout for this fragment
        return view
    }


}