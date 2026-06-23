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
import com.cibertec.proyectodam1.Models.Usuario
import com.cibertec.proyectodam1.PerfilBottomSheet
import com.cibertec.proyectodam1.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PerfilFragment : Fragment() {
    private lateinit var txtUsuarioPerfil: TextView
    private lateinit var optionDatosPersonales: LinearLayout
    private lateinit var optionMisReservas: LinearLayout // Nueva opción
    private lateinit var optionCerrarSesion: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        // Inicializar vistas
        txtUsuarioPerfil = view.findViewById(R.id.txtUsuarioPerfil)
        optionDatosPersonales = view.findViewById(R.id.optionDatosPersonales)
        optionMisReservas = view.findViewById(R.id.optionMisReservas)
        optionCerrarSesion = view.findViewById(R.id.optionCerrarSesion)

        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        val uid = auth.currentUser?.uid

        if (uid != null){
            db.collection("usuarios").document(uid).get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()){
                        val usuario = doc.toObject(Usuario::class.java)
                        if (usuario != null){
                            txtUsuarioPerfil.text = usuario.nombre
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


        optionDatosPersonales.setOnClickListener {
            val bottom = PerfilBottomSheet()
            bottom.show(parentFragmentManager, "PerfilBottomSheet")
            mostrarMensaje("Abriendo Datos personales")
        }


        optionMisReservas.setOnClickListener {
            val intent = Intent(requireContext(), MisReservasActivity::class.java)
            startActivity(intent)
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

    fun mostrarMensaje(mensaje: String){
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
    }
}