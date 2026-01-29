package com.duoc.ecorentfinal.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duoc.ecorentfinal.viewmodel.AuthViewModel
import androidx.compose.material3.CircularProgressIndicator

import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val authViewModel: AuthViewModel = viewModel()
    val registroState by authViewModel.registroState.collectAsStateWithLifecycle()

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var nombreError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var telefonoError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    var generalError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(registroState) {
        when (val state = registroState) {  // ← Declarar variable DENTRO del when
            is AuthViewModel.RegistroState.Success -> {
                onRegisterSuccess()
            }
            is AuthViewModel.RegistroState.Error -> {
                generalError = state.mensaje
            }
            else -> {}
        }
    }
    // Función para validar nombre (solo letras y espacios)
    fun validarNombre(nombre: String): String? {
        return when {
            nombre.isEmpty() -> "Nombre requerido"
            nombre.any { it.isDigit() } -> "No puede contener números"
            nombre.length < 3 -> "Mínimo 3 caracteres"
            else -> null
        }
    }

    // Función para validar email
    fun validarEmail(email: String): String? {
        val emailRegex = Regex("""^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$""")
        return when {
            email.isEmpty() -> "Email requerido"
            !email.contains("@") -> "Debe contener @"
            email.count { it == '@' } > 1 -> "Solo un @ permitido"
            !emailRegex.matches(email) -> "Formato inválido (ej: usuario@dominio.com)"
            else -> null
        }
    }

    // Función para validar teléfono (9+ dígitos, solo números)
    fun validarTelefono(telefono: String): String? {
        val soloNumeros = telefono.filter { it.isDigit() }
        return when {
            telefono.isEmpty() -> "Teléfono requerido"
            soloNumeros.length < 9 -> "Mínimo 9 dígitos"
            telefono.any { it.isLetter() } -> "Solo números"
            else -> null
        }
    }

    // Función para validar contraseña (alfanumérica + mayúscula)
    fun validarPassword(password: String): String? {
        val tieneMayuscula = password.any { it.isUpperCase() }
        val tieneNumero = password.any { it.isDigit() }
        val tieneLetra = password.any { it.isLetter() }

        return when {
            password.isEmpty() -> "Contraseña requerida"
            password.length < 8 -> "Mínimo 8 caracteres"
            !tieneMayuscula -> "Debe tener al menos una mayúscula"
            !tieneNumero -> "Debe tener al menos un número"
            !tieneLetra -> "Debe tener al menos una letra"
            else -> null
        }
    }

    // Función para validar confirmación de contraseña
    fun validarConfirmPassword(password: String, confirmPassword: String): String? {
        return when {
            confirmPassword.isEmpty() -> "Confirmar contraseña"
            password != confirmPassword -> "Las contraseñas no coinciden"
            else -> null
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "📝 Registrarse",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Crea tu cuenta en EcoRent",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary
            )
            // Mostrar error general
            if (generalError != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = generalError!!,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp
                )
            }
            // Mostrar loader si está cargando
            if (registroState is AuthViewModel.RegistroState.Loading) {
                // Kotlin hará smart cast aquí porque no está dentro de LaunchedEffect
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Campo Nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    nombreError = validarNombre(it)
                    generalError = null
                },
                label = { Text("Nombre completo") },
                isError = nombreError != null,
                singleLine = true,
                modifier = Modifier.width(300.dp),
                supportingText = {
                    if (nombreError != null) {
                        Text(text = nombreError!!, color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Campo Email
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = validarEmail(it)
                    generalError = null
                },
                label = { Text("Email") },
                isError = emailError != null,
                singleLine = true,
                modifier = Modifier.width(300.dp),
                supportingText = {
                    if (emailError != null) {
                        Text(text = emailError!!, color = MaterialTheme.colorScheme.error)
                    } else {
                        Text(text = "ej: usuario@gmail.com")
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Campo Teléfono
            OutlinedTextField(
                value = telefono,
                onValueChange = {
                    telefono = it
                    telefonoError = validarTelefono(it)
                    generalError = null
                },
                label = { Text("Teléfono") },
                isError = telefonoError != null,
                singleLine = true,
                modifier = Modifier.width(300.dp),
                supportingText = {
                    if (telefonoError != null) {
                        Text(text = telefonoError!!, color = MaterialTheme.colorScheme.error)
                    } else {
                        Text(text = "Mínimo 9 dígitos")
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Campo Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = validarPassword(it)
                    // Re-validar confirmación si hay cambios
                    if (confirmPassword.isNotEmpty()) {
                        confirmPasswordError = validarConfirmPassword(it, confirmPassword)
                        generalError = null
                    }
                },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = passwordError != null,
                singleLine = true,
                modifier = Modifier.width(300.dp),
                supportingText = {
                    if (passwordError != null) {
                        Text(text = passwordError!!, color = MaterialTheme.colorScheme.error)
                    } else {
                        Text(text = "8+ chars, 1 mayúscula, 1 número")
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Campo Confirmar Contraseña
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    confirmPasswordError = validarConfirmPassword(password, it)
                    generalError = null
                },
                label = { Text("Confirmar contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = confirmPasswordError != null,
                singleLine = true,
                modifier = Modifier.width(300.dp),
                supportingText = {
                    if (confirmPasswordError != null) {
                        Text(text = confirmPasswordError!!, color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botón Registrarse
            Button(
                onClick = {
                    // Validar todos los campos
                    nombreError = validarNombre(nombre)
                    emailError = validarEmail(email)
                    telefonoError = validarTelefono(telefono)
                    passwordError = validarPassword(password)
                    confirmPasswordError = validarConfirmPassword(password, confirmPassword)

                    val hasErrors = listOf(
                        nombreError, emailError, telefonoError,
                        passwordError, confirmPasswordError
                    ).any { it != null }

                    if (!hasErrors) {
                        // TODO: Guardar usuario en Room Database
                        authViewModel.registrarUsuario(nombre, email, telefono, password)
                    }
                },
                modifier = Modifier.width(200.dp),
                enabled = registroState !is AuthViewModel.RegistroState.Loading
            ) {
                Text(text = "Registrarse")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Enlace a Login
            TextButton(
                onClick = onNavigateToLogin,
                        enabled = registroState !is AuthViewModel.RegistroState.Loading
            ) {
                Text(
                    text = "¿Ya tienes cuenta? Inicia sesión",
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón Volver
            TextButton(
                onClick = onNavigateBack,
                        enabled = registroState !is AuthViewModel.RegistroState.Loading
            ) {
                Text(text = "Volver al Inicio")
            }
        }
    }
}