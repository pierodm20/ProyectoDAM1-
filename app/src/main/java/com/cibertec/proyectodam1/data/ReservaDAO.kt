package com.cibertec.proyectodam1.data

import android.content.Context
import com.cibertec.proyectodam1.db.ConexionDB

class ReservaDAO(context: Context) {
    private val conexion = ConexionDB(context)

    // Inserta usando el método de ConexionDB
    fun insertarReserva(idHotel: Int, correo: String, fecha: String): Boolean {
        val resultado = conexion.registrarReserva(idHotel, correo, fecha)
        return resultado != -1L // Si es -1, significa que falló la inserción
    }

    // Filtra las reservas usando el método de ConexionDB
    fun obtenerReservasPorUsuario(correoUsuario: String): List<Map<String, Any>> {
        val lista = ArrayList<Map<String, Any>>()
        val cursor = conexion.obtenerReservasPorUsuario(correoUsuario)

        if (cursor.moveToFirst()) {
            do {
                val reserva = HashMap<String, Any>()
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