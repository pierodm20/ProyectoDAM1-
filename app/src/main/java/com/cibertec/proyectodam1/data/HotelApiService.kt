package com.cibertec.proyectodam1.data


import com.cibertec.proyectodam1.Models.Hotel
import retrofit2.http.GET

interface HotelApiService {
    @GET("hoteles")
    suspend fun getHoteles(): List<Hotel>
}