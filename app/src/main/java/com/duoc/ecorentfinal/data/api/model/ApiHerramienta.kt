package com.duoc.ecorentfinal.data.api.model

import com.google.gson.annotations.SerializedName

data class ApiHerramienta(
    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
    val nombre: String,

    @SerializedName("body")
    val descripcion: String,

    @SerializedName("userId")
    val categoriaId: Long
)