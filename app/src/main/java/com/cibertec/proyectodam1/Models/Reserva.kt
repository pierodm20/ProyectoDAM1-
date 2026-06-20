package com.cibertec.proyectodam1.Models

data class Reserva(
    val id_reserva: Int,
    val id_hotel: Int,
    val correo_usuario: String,
    val fecha: String
)