package com.cibertec.proyectodam1.Entitys

data class Hotel(
    var id: Int,
    var nombre: String,
    var ciudad : String,
    var estrellas : Int,
    var precioXnoche: Double,
    var imagen: String
)