package com.duoc.ecorentfinal.data.repository

import com.duoc.ecorentfinal.data.dataBase.EcoRentDatabase
import com.duoc.ecorentfinal.data.dataBase.UsuarioDao
import com.duoc.ecorentfinal.data.entities.UsuarioEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    // Insertar usuario
    suspend fun insertUsuario(usuario: UsuarioEntity): Long {
        return withContext(Dispatchers.IO) {
            usuarioDao.insertUsuario(usuario)
        }
    }

    // Obtener usuario por email
    suspend fun getUsuarioByEmail(email: String): UsuarioEntity? {
        return withContext(Dispatchers.IO) {
            usuarioDao.getUsuarioByEmail(email)
        }
    }

    // Verificar si email ya existe
    suspend fun emailExiste(email: String): Boolean {
        return withContext(Dispatchers.IO) {
            usuarioDao.getUsuarioByEmail(email) != null
        }
    }

    // Validar login
    suspend fun validarLogin(email: String, password: String): UsuarioEntity? {
        return withContext(Dispatchers.IO) {
            val usuario = usuarioDao.getUsuarioByEmail(email)
            if (usuario != null && usuario.passwordHash == password.hashCode().toString()) {
                usuario
            } else {
                null
            }
        }
    }
}