package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.core

import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.LocationsResponse
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.MenuResponse
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.RegistrationRequest
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.models.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {
    @POST("/auth/register")
    suspend fun registration(@Body registrationRequest: RegistrationRequest): RegistrationResponse

    @POST("/auth/login")
    suspend fun login(@Body loginRequest: RegistrationRequest): RegistrationResponse

    @GET("/locations")
    suspend fun getLocations(): List<LocationsResponse>

    @GET("location/{id}/menu")
    suspend fun getMenu(@Path("id") id: Int): List<MenuResponse>
}
