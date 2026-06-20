package com.cibertec.proyectodam1

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cibertec.proyectodam1.db.ConexionDB
import com.google.firebase.auth.FirebaseAuth

class DetalleReservaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_reserva)

        //
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val idHotel = intent.getIntExtra("id_hotel", 0)

        val btnReservar = findViewById<Button>(R.id.btnReservar)

        btnReservar.setOnClickListener {

            val user = FirebaseAuth.getInstance().currentUser
            val correoUsuario = user?.email

            if (correoUsuario != null) {

                val db = ConexionDB(this)
                val fechaActual = "2026-06-19"

                val resultado = db.registrarReserva(idHotel, correoUsuario, fechaActual)

                if (resultado != -1L) {
                    Toast.makeText(this, "Reserva confirmada para $correoUsuario", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al guardar reserva", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Error: Usuario no autenticado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}