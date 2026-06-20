package com.cibertec.proyectodam1.Models

data class Hotel(
    val id: Int,
    val nombre: String,
    val ciudad: String,
    val estrellas: Int,
    val precio: Double,
    val imagen: String
)