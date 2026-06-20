package com.cibertec.proyectodam1

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.proyectodam1.Adapters.ReservaAdapter
import com.cibertec.proyectodam1.Models.Reserva
import com.cibertec.proyectodam1.data.ReservaDAO
import com.google.firebase.auth.FirebaseAuth

class MisReservasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mis_reservas)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rvMisReservas = findViewById<RecyclerView>(R.id.rvMisReservas)
        val tvMensajeVacio = findViewById<TextView>(R.id.tvMensajeVacio)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        rvMisReservas.layoutManager = LinearLayoutManager(this)
        progressBar.visibility = View.VISIBLE

        var correo = FirebaseAuth.getInstance().currentUser?.email ?: ""

        if (correo.isEmpty()) {
            correo = "donayrep20@gmail.com"
        }

        //  Usar ReservaDAO en lugar de llamar a ConexionDB directo
        val reservaDAO = ReservaDAO(this)
        val data = reservaDAO.obtenerReservasPorUsuario(correo)

        //  Convertir Map a Lista de Modelos (Reserva)
        val listaReservas = data.map {
            Reserva(
                id_reserva = it["id_reserva"] as Int,
                id_hotel = it["id_hotel"] as Int,
                correo_usuario = it["correo_usuario"] as String,
                fecha = it["fecha"] as String
            )
        }

        progressBar.visibility = View.GONE

        if (listaReservas.isNotEmpty()) {
            rvMisReservas.adapter = ReservaAdapter(listaReservas)
            rvMisReservas.visibility = View.VISIBLE
            tvMensajeVacio.visibility = View.GONE
        } else {
            rvMisReservas.visibility = View.GONE
            tvMensajeVacio.visibility = View.VISIBLE
        }
    }
}