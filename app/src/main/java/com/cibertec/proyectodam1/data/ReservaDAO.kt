package com.cibertec.proyectodam1.data

import android.content.Context
import com.cibertec.proyectodam1.db.ConexionDB
import com.google.firebase.firestore.FirebaseFirestore

class ReservaDAO(context: Context) {
    private val conexion = ConexionDB(context)
    private val dbFirestore = FirebaseFirestore.getInstance() // Instancia para la Nube

    // Inserta usando el método de ConexionDB y sincroniza con Firebase
    fun insertarReserva(idHotel: Int, correo: String, fecha: String): Boolean {
        // 1. Guardar en SQLite local
        val resultado = conexion.registrarReserva(idHotel, correo, fecha)
        val esExitoso = resultado != -1L

        // 2. Si se guardó bien localmente, sincronizamos con Firebase Firestore
        if (esExitoso) {
            val reservaNube = hashMapOf(
                "id_hotel" to idHotel,
                "correo_usuario" to correo,
                "fecha" to fecha,
                "timestamp" to System.currentTimeMillis()
            )

            dbFirestore.collection("reservas")
                .add(reservaNube)
                .addOnSuccessListener {
                    // Reserva sincronizada en la nube exitosamente
                }
                .addOnFailureListener {
                    // Opcional: manejar si falla la subida a la nube
                }
        }

        return esExitoso
    }

    // Filtra las reservas usando el método de ConexionDB
    fun obtenerReservasPorUsuario(correoUsuario: String): List<Map<String, Any>> {
        val lista = ArrayList<Map<String, Any>>()
        val cursor = conexion.obtenerReservasPorUsuario(correoUsuario)

        if (cursor.moveToFirst()) {
            do {
                val reserva = HashMap<String, Any>()
                // Mantenemos tu lógica de índices tal cual
                reserva["id_reserva"] = cursor.getInt(cursor.getColumnIndexOrThrow("id_reserva"))
                reserva["id_hotel"] = cursor.getInt(cursor.getColumnIndexOrThrow("id_hotel"))
                reserva["correo_usuario"] = cursor.getString(cursor.getColumnIndexOrThrow("correo_usuario"))
                reserva["fecha"] = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))
                lista.add(reserva)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }
}