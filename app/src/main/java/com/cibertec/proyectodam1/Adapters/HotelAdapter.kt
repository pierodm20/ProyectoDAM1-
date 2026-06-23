package com.cibertec.proyectodam1.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cibertec.proyectodam1.Models.Hotel
import com.cibertec.proyectodam1.R

class HotelAdapter(
    private var hoteles: List<Hotel>,
    val context: Activity,
    private val onItemClick: (Int) -> Unit
): RecyclerView.Adapter<HotelAdapter.HotelAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hotel, parent, false)
        return HotelAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: HotelAdapterViewHolder, position: Int) {
        val hotel = hoteles[position]

        Glide.with(context).load(hotel.imagen).into(holder.ivHImagen)
        holder.tvHNombre.text = hotel.nombre
        holder.tvHCiudad.text = hotel.ciudad
        holder.tvHEstrellas.text = "⭐".repeat(hotel.estrellas)
        holder.tvHPrecioXNoche.text = "Precio por noche: S/. ${hotel.precio}"

        // Al hacer clic en la tarjeta (itemView), enviamos el hotel seleccionado
        holder.itemView.setOnClickListener {
            onItemClick(hotel.id)
        }
    }

    override fun getItemCount(): Int {
        return hoteles.size
    }

    inner class HotelAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvHNombre: TextView = itemView.findViewById<TextView>(R.id.tvNombre)
        val tvHCiudad: TextView = itemView.findViewById<TextView>(R.id.tvCiudad)
        val tvHEstrellas : TextView = itemView.findViewById<TextView>(R.id.tvEstrellas)
        val tvHPrecioXNoche: TextView = itemView.findViewById<TextView>(R.id.tvPrecio)
        val ivHImagen: ImageView = itemView.findViewById<ImageView>(R.id.ivImagen)
    }

    fun actualizarLista(nuevaLista: List<Hotel>){
        this.hoteles = nuevaLista
        notifyDataSetChanged()
    }
}