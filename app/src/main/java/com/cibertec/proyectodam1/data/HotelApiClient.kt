package com.cibertec.proyectodam1.data
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object HotelApiClient {

    //  URL base de mockapi.io
    private const val BASE_URL = "https://6a3a9999917c7b14c74dd8ad.mockapi.io/api/"

    val apiService: HotelApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HotelApiService::class.java)
    }
}