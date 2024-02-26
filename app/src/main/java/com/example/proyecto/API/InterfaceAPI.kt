package com.example.proyecto.API

import com.example.proyecto.Requests.CityRequest
import com.example.proyecto.Requests.LugarRequest
import com.example.proyecto.Requests.ResenyaRequest
import com.example.proyecto.Responses.CityResponse
import com.example.proyecto.Responses.LoginResponse
import com.example.proyecto.Responses.LugarResponse
import com.example.proyecto.Responses.ResenyaResponse
import com.example.proyecto.Responses.UsuariResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface InterfaceAPI {

    @POST
    suspend fun login(@Url url: String): Response<LoginResponse>

    @GET
    suspend fun getCiudades(@Url url:String) : Response<List<CityResponse>>

    @GET
    suspend fun getLugares(@Url url: String) : Response<List<LugarResponse>>

    @GET
    suspend fun getResenyas(@Url url: String) : Response<List<ResenyaResponse>>

    @POST
    suspend fun infoLogin(@Url url: String): Response<UsuariResponse.UsuariList>

    @POST("/ciudades")
    suspend fun postCiudad(@Body ciudad: CityRequest)

    @POST("/lugares")
    suspend fun postLugar(@Body lugar: LugarRequest)

    @POST("/reseñas")
    suspend fun postResenya(@Body resenya: ResenyaRequest): Response<ResenyaResponse>

    @POST
    suspend fun comprarPremium(@Url url: String)

}