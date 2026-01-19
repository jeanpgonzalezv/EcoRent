package com.duoc.ecorentfinal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.ecorentfinal.data.dataBase.EcoRentDatabase
import com.duoc.ecorentfinal.data.entities.UsuarioEntity
import com.duoc.ecorentfinal.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UsuarioRepository

    init {
        val usuarioDao = EcoRentDatabase.getDatabase(application).usuarioDao()
        repository = UsuarioRepository(usuarioDao)
    }

    // Estados para el registro
    private val _registroState = MutableStateFlow<RegistroState>(RegistroState.Idle)
    val registroState: StateFlow<RegistroState> = _registroState.asStateFlow()

    // Estados para el login
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    // Registrar nuevo usuario
    fun registrarUsuario(
        nombre: String,
        email: String,
        telefono: String,
        password: String
    ) {
        viewModelScope.launch {
            _registroState.value = RegistroState.Loading

            try {
                // Verificar si el email ya existe
                val emailExiste = repository.emailExiste(email)
                if (emailExiste) {
                    _registroState.value = RegistroState.Error("El email ya está registrado")
                    return@launch
                }

                // Crear entidad de usuario
                val usuario = UsuarioEntity(
                    nombre = nombre,
                    email = email,
                    telefono = telefono,
                    passwordHash = password.hashCode().toString() // Hash simple por ahora
                )

                // Insertar en la base de datos
                val id = repository.insertUsuario(usuario)

                if (id > 0) {
                    _registroState.value = RegistroState.Success(usuario)
                } else {
                    _registroState.value = RegistroState.Error("Error al registrar usuario")
                }
            } catch (e: Exception) {
                _registroState.value = RegistroState.Error("Error: ${e.message}")
            }
        }
    }

    // Iniciar sesión
    fun iniciarSesion(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            try {
                val usuario = repository.validarLogin(email, password)
                if (usuario != null) {
                    _loginState.value = LoginState.Success(usuario)
                } else {
                    _loginState.value = LoginState.Error("Credenciales incorrectas")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Error: ${e.message}")
            }
        }
    }

    // Estados del registro
    sealed class RegistroState {
        object Idle : RegistroState()
        object Loading : RegistroState()
        data class Success(val usuario: UsuarioEntity) : RegistroState()
        data class Error(val mensaje: String) : RegistroState()
    }

    // Estados del login
    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(val usuario: UsuarioEntity) : LoginState()
        data class Error(val mensaje: String) : LoginState()
    }
}