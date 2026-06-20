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
import androidx.appcompat.app.AlertDialog
import com.cibertec.proyectodam1.LoginActivity
import com.cibertec.proyectodam1.MisReservasActivity
import com.cibertec.proyectodam1.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class PerfilFragment : Fragment() {
    private lateinit var txtUsuarioPerfil: TextView
    private lateinit var optionDatosPersonales: LinearLayout
    private lateinit var optionMisReservas: LinearLayout // Nueva opción
    private lateinit var optionConfiguraciones: LinearLayout
    private lateinit var optionCerrarSesion: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        // Inicializar vistas
        txtUsuarioPerfil = view.findViewById(R.id.txtUsuarioPerfil)
        optionDatosPersonales = view.findViewById(R.id.optionDatosPersonales)
        optionMisReservas = view.findViewById(R.id.optionMisReservas)
        optionConfiguraciones = view.findViewById(R.id.optionConfiguraciones)
        optionCerrarSesion = view.findViewById(R.id.optionCerrarSesion)

        txtUsuarioPerfil.text = "Piero Donayre"


        optionDatosPersonales.setOnClickListener {
            Toast.makeText(requireContext(), "Abriendo Datos personales", Toast.LENGTH_SHORT).show()
        }


        optionMisReservas.setOnClickListener {
            val intent = Intent(requireContext(), MisReservasActivity::class.java)
            startActivity(intent)
        }

        optionConfiguraciones.setOnClickListener {
            Toast.makeText(requireContext(), "Abriendo Configuraciones", Toast.LENGTH_SHORT).show()
        }

        optionCerrarSesion.setOnClickListener {
            val dialog = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_cerrar_sesion, null)
            val builder = AlertDialog.Builder(requireContext())
            builder.setView(dialog)
            val alertDialog = builder.create()
            alertDialog.show()

            val btnCancelar = dialog.findViewById<MaterialButton>(R.id.btnCancelar)
            val btnConfirmarCerrar = dialog.findViewById<MaterialButton>(R.id.btnConfirmarCerrar)

            btnCancelar.setOnClickListener { alertDialog.dismiss() }
            btnConfirmarCerrar.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                alertDialog.dismiss()
                requireActivity().finish()
            }
        }
        return view
    }
}