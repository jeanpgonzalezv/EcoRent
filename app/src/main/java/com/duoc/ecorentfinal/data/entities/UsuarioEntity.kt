package com.duoc.ecorentfinal.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val email: String,
    val telefono: String,
    val passwordHash: String,
    val rol: String = "cliente", // "cliente" o "admin"
    val fotoPerfilUri: String? = null
)