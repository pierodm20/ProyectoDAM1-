package com.cibertec.proyectodam1.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cibertec.proyectodam1.Entitys.Hotel
import com.cibertec.proyectodam1.R

class HotelAdapter(private val hoteles: List<Hotel>, val context: Activity): RecyclerView.Adapter<HotelAdapter.HotelAdapterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hotel, parent, false)
        return HotelAdapterViewHolder(view)
    }
    override fun onBindViewHolder(holder: HotelAdapterViewHolder, position: Int) {
        val hotel = hoteles[position]
        Glide.with(context).load(hotel.imagen).into(holder.ivHImagen)
        holder.tvHNombre.text = hotel.nombre
        holder.tvHCapacidad.text = "S/. ${hotel.capacidad}"
        holder.tvHPrecio.text = "S/. ${hotel.precio}"
    }
    override fun getItemCount(): Int {
        return hoteles.size
    }
    inner class HotelAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvHNombre: TextView = itemView.findViewById<TextView>(R.id.tvHNombre)
        val ivHImagen: ImageView = itemView.findViewById<ImageView>(R.id.ivHImagen)
        val tvHCapacidad: TextView = itemView.findViewById<TextView>(R.id.tvHCapacidad)
        val tvHPrecio: TextView = itemView.findViewById<TextView>(R.id.tvHPrecio)
    }
}