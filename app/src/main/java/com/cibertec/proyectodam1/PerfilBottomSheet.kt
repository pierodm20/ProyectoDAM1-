package com.cibertec.proyectodam1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.cibertec.proyectodam1.Models.Usuario
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore

class PerfilBottomSheet: BottomSheetDialogFragment() {
    private lateinit var txtNombreApellido: TextView
    private lateinit var txtTelefono: TextView
    private lateinit var txtCorreo: TextView
    private lateinit var txtFecha: TextView
    private  val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.item_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        txtNombreApellido = view.findViewById<TextView>(R.id.txtNombreApellido)
        txtTelefono = view.findViewById<TextView>(R.id.txtTelefono)
        txtCorreo = view.findViewById<TextView>(R.id.txtCorreo)
        txtFecha = view.findViewById<TextView>(R.id.txtFecha)

        val uid = auth.currentUser?.uid

        if (uid != null){
            db.collection("usuarios").document(uid).get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()){
                        val usuario = doc.toObject(Usuario::class.java)
                        if (usuario != null){
                            txtNombreApellido.text = "${usuario.nombre} ${usuario.apellido}"
                            txtTelefono.text = usuario.telefono
                            txtCorreo.text = usuario.correo
                            txtFecha.text = usuario.fecha
                        }else{
                            mostrarMensaje("No se encotro datos del usuario")
                        }
                    }
                }
                .addOnFailureListener { e ->
                    mostrarMensaje("Error al cargar el perfil: ${e.message}")
                }
        }else{
            mostrarMensaje("El usuario no esta logeado")
        }


        super.onViewCreated(view, savedInstanceState)
    }

    fun mostrarMensaje(mensaje: String){
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
    }
}