package com.cibertec.proyectodam1.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.proyectodam1.Models.Reserva
import com.cibertec.proyectodam1.R

class ReservaAdapter(private val listaReservas: List<Reserva>) : RecyclerView.Adapter<ReservaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvIdHotel: TextView = view.findViewById(R.id.tvHotel)
        val tvFecha: TextView = view.findViewById(R.id.tvFecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reserva, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reserva = listaReservas[position]
        holder.tvIdHotel.text = "Hotel ID: ${reserva.id_hotel}"
        holder.tvFecha.text = "Reservado el: ${reserva.fecha}"
    }

    override fun getItemCount() = listaReservas.size
}