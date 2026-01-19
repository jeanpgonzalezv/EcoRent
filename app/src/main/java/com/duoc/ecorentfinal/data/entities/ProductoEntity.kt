package com.duoc.ecorentfinal.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class ProductoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val descripcion: String,
    val categoria: String,
    val precioPorDia: Double,
    val stock: Int,
    val imagenUri: String? = null,
    val rating: Float = 0.0f,
    val disponible: Boolean = true
)