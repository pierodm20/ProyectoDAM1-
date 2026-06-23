package com.cibertec.proyectodam1.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.proyectodam1.R
import com.google.android.material.button.MaterialButton
import java.util.zip.Inflater

class TutorialAdapter(private val titulo: List<String>, private val descripciones: List<String>): RecyclerView.Adapter<TutorialAdapter.TituloViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TituloViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tutorial, parent, false)
        return TituloViewHolder(view)
    }

    override fun onBindViewHolder(holder: TituloViewHolder, position: Int) {
        holder.txtTitulo.text = titulo[position]
        holder.txtDescrip.text = descripciones[position]
        holder.txtTitulo.alpha = 0f
        holder.txtTitulo.translationY = 50f // Empieza un poco abajo
        // Animamos la aparición
        holder.txtTitulo.animate()
            .alpha(1f)
            .translationY(0f) // Sube a su posición original
            .setDuration(800)
            .start()
    }

    override fun getItemCount(): Int {
        return descripciones.size
    }

    inner class TituloViewHolder(item: View): RecyclerView.ViewHolder(item){
        val txtTitulo: TextView = item.findViewById<TextView>(R.id.txtTitulo)
        val txtDescrip: TextView = item.findViewById<TextView>(R.id.txtDescrip)
    }
}