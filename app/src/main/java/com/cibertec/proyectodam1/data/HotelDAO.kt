package com.cibertec.proyectodam1.data

import android.content.Context
import com.cibertec.proyectodam1.db.ConexionDB

class HotelDAO(context: Context) {
    private val conexion = ConexionDB(context)

    //  Lee los hoteles locales de ConexionDB
    fun obtenerTodosLosHoteles(): List<Map<String, Any>> {
        val lista = ArrayList<Map<String, Any>>()
        val cursor = conexion.obtenerHoteles()

        if (cursor.moveToFirst()) {
            do {
                val hotel = HashMap<String, Any>()
                // Mapeamos las columnas exactas de la tabla HOTEL
                hotel["id_hotel"] = cursor.getInt(cursor.getColumnIndexOrThrow("id_hotel"))
                hotel["nombre"] = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                hotel["ciudad"] = cursor.getString(cursor.getColumnIndexOrThrow("ciudad"))
                hotel["estrellas"] = cursor.getInt(cursor.getColumnIndexOrThrow("estrellas"))
                hotel["precioXnoche"] = cursor.getDouble(cursor.getColumnIndexOrThrow("precioXnoche"))
                hotel["imagen"] = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))
                lista.add(hotel)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }
}