package com.duoc.ecorentfinal.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alquileres")
data class AlquilerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val usuarioId: Long,
    val productoId: Long,
    val fechaInicio: Long, // Timestamp
    val fechaFin: Long,
    val cantidadDias: Int,
    val costoTotal: Double,
    val estado: String = "pendiente", // "pendiente", "activo", "completado"
    val fechaCreacion: Long = System.currentTimeMillis()
)