package com.duoc.ecorentfinal.data.api

import com.duoc.ecorentfinal.data.api.model.ApiHerramienta
import retrofit2.http.GET

interface HerramientasApiService {
    @GET("posts")
    suspend fun getHerramientas(): List<ApiHerramienta>

    @GET("posts/{id}")
    suspend fun getHerramientaById(@retrofit2.http.Path("id") id: Long): ApiHerramienta
}