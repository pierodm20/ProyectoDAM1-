package com.cibertec.proyectodam1.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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

        //  agregamos una transición suave y manejo de error por si la URL falla
        Glide.with(context)
            .load(hotel.imagen)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.ivHImagen)

        holder.tvHNombre.text = hotel.nombre
        holder.tvHCiudad.text = hotel.ciudad

        // Manejo seguro para evitar errores si las estrellas son negativas o muy altas
        val estrellasCount = if (hotel.estrellas > 0) hotel.estrellas else 0
        holder.tvHEstrellas.text = "⭐".repeat(estrellasCount)

        holder.tvHPrecioXNoche.text = "Precio por noche: S/. ${hotel.precio}"

        holder.itemView.setOnClickListener {
            onItemClick(hotel.id)
        }
    }

    override fun getItemCount(): Int {
        return hoteles.size
    }

    inner class HotelAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvHNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvHCiudad: TextView = itemView.findViewById(R.id.tvCiudad)
        val tvHEstrellas : TextView = itemView.findViewById(R.id.tvEstrellas)
        val tvHPrecioXNoche: TextView = itemView.findViewById(R.id.tvPrecio)
        val ivHImagen: ImageView = itemView.findViewById(R.id.ivImagen)
    }

    fun actualizarLista(nuevaLista: List<Hotel>){
        this.hoteles = nuevaLista
        notifyDataSetChanged()
    }
}