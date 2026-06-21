package com.cibertec.proyectodam1.data

import android.content.Context
import android.widget.Toast
import com.cibertec.proyectodam1.Models.Hotel
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

    fun obtenerDetalleHotel(idHotel: Int): Hotel?{
        val cursor = conexion.obtenerHotelPorId(idHotel)
        var hotel : Hotel? = null
        if (cursor.moveToFirst()){
            hotel = Hotel(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id_hotel")),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                ciudad = cursor.getString(cursor.getColumnIndexOrThrow("ciudad")),
                estrellas = cursor.getInt(cursor.getColumnIndexOrThrow("estrellas")),
                precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precioXnoche")),
                imagen = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))
            )
        }
        cursor.close()
        return hotel
    }
}