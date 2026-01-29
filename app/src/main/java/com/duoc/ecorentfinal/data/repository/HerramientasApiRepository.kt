package com.duoc.ecorentfinal.data.repository

import com.duoc.ecorentfinal.data.api.RetrofitClient
import com.duoc.ecorentfinal.data.api.model.toHerramienta
import com.duoc.ecorentfinal.data.model.Herramienta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HerramientasApiRepository {
    private val apiService = RetrofitClient.herramientasApiService

    suspend fun getHerramientasFromApi(): List<Herramienta> {
        return withContext(Dispatchers.IO) {
            try {
                val apiHerramientas = apiService.getHerramientas()
                apiHerramientas.map { it.toHerramienta() }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
}