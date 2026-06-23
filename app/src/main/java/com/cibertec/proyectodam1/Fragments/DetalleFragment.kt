package com.cibertec.proyectodam1.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.cibertec.proyectodam1.R
import com.cibertec.proyectodam1.data.HotelDAO
import com.cibertec.proyectodam1.data.ReservaDAO
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class DetalleFragment : Fragment() {

    private lateinit var ivImagenDet: ImageView
    private lateinit var txtNombre: TextView
    private lateinit var txtCiudad: TextView
    private lateinit var txtEstrellas: TextView
    private lateinit var txtPrecio: TextView
    private lateinit var txtInFechaReserva: TextInputEditText
    private lateinit var btnReservar: MaterialButton
    private lateinit var ivAtras: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detalle, container, false)
        ivImagenDet = view.findViewById(R.id.ivImagenDet)
        txtNombre = view.findViewById(R.id.txtNombre)
        txtCiudad = view.findViewById(R.id.txtCiudad)
        txtEstrellas = view.findViewById(R.id.txtEstrellas)
        txtPrecio = view.findViewById(R.id.txtPrecio)
        txtInFechaReserva = view.findViewById(R.id.txtInFechaReserva)
        btnReservar = view.findViewById(R.id.btnReservar)
        ivAtras = view.findViewById(R.id.ivAtras)

        val idHotel = arguments?.getInt("id", -1) ?: -1


        val hotelDAO = HotelDAO(requireContext())

        if (idHotel != -1) {
            val hotel = hotelDAO.obtenerDetalleHotel(idHotel)
            if (hotel != null) {

                Glide.with(requireContext()).load(hotel.imagen).into(ivImagenDet)
                txtNombre.text = hotel.nombre
                txtCiudad.text = hotel.ciudad
                txtEstrellas.text = "⭐".repeat(hotel.estrellas)
                txtPrecio.text = "Precio por noche: S/. ${hotel.precio}"
            } else {
                mostrarMensaje("Hotel cargado desde API")

            }
        } else {
            mostrarMensaje("ID del hotel no recibido")
        }

        ivAtras.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        btnReservar.setOnClickListener {
            val fecha = txtInFechaReserva.text.toString()
            val share = requireActivity().getSharedPreferences("Sesion usuario", Context.MODE_PRIVATE)
            val usuarioLogeado = share.getString("correo", "") ?: ""

            if (usuarioLogeado.isEmpty()){
                mostrarMensaje("Error: Debes iniciar sesión para reservar")
                return@setOnClickListener
            }

            if (fecha.isEmpty()){
                mostrarMensaje("Seleccione una fecha para la reserva")
                return@setOnClickListener
            }

            val reservaDAO = ReservaDAO(requireContext())
            val esReservaExitosa = reservaDAO.insertarReserva(idHotel, usuarioLogeado, fecha)

            if(esReservaExitosa){
                mostrarMensaje("Reserva exitosa")
                parentFragmentManager.popBackStack()
            } else {
                mostrarMensaje("Hubo un error en la reserva")
            }
        }
        return view
    }

    fun mostrarMensaje(mensaje: String){
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
    }
}