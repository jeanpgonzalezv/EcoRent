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
import androidx.compose.material3.CircularProgressIndicator
import com.duoc.ecorentfinal.viewmodel.AuthViewModel

import androidx.compose.runtime.collectAsState



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val authViewModel: AuthViewModel = viewModel()
    val loginState by authViewModel.loginState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    var generalError by remember { mutableStateOf<String?>(null) }

    //EFFECT PARA OBSERVAR CAMBIOS:
    LaunchedEffect(loginState) {
        when (loginState) {
            is AuthViewModel.LoginState.Success -> {
                onLoginSuccess()
            }
            is AuthViewModel.LoginState.Error -> {
                generalError = (loginState as AuthViewModel.LoginState.Error).mensaje
            }
            else -> {}
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
                text = "🔐 Iniciar Sesión",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            //Mostrar error general
            if (generalError != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = generalError!!,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp
                )
            }

            //Mostrar loader si está cargando
            if (loginState is AuthViewModel.LoginState.Loading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }


            Spacer(modifier = Modifier.height(32.dp))

            // Campo Email
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = false
                    generalError = null
                },
                label = { Text("Email") },
                isError = emailError,
                singleLine = true,
                modifier = Modifier.width(280.dp)
            )

            if (emailError) {
                Text(
                    text = "Email inválido",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = false
                    generalError = null
                },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = passwordError,
                singleLine = true,
                modifier = Modifier.width(280.dp)
            )

            if (passwordError) {
                Text(
                    text = "Mínimo 6 caracteres",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón Login
            Button(
                onClick = {
                    println("🟡 DEBUG: Botón presionado - Email: $email, Pass: $password")

                    // Validaciones SUPER básicas
                    val emailValido = email.isNotEmpty() && email.contains("@")
                    val passValido = password.isNotEmpty()

                    if (emailValido && passValido) {
                        println("🟢 DEBUG: Llamando a ViewModel")
                        authViewModel.iniciarSesion(email, password)

                        // PRUEBA: También llamar a debug
                        authViewModel.debugVerUsuarios()
                    } else {
                        println("🔴 DEBUG: Validaciones fallaron")
                        emailError = !emailValido
                        passwordError = !passValido
                    }
                },
                modifier = Modifier.width(200.dp)
            ) {
                Text(text = "Ingresar")
            }

            Spacer(modifier = Modifier.height(16.dp))
            // Enlace a Registro
            TextButton(onClick = onNavigateToRegister) {
                Text(
                    text = "¿No tienes cuenta? Regístrate",
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Botón Volver
            TextButton(
                onClick = onNavigateBack,
                enabled = loginState !is AuthViewModel.LoginState.Loading
            ) {
                Text(text = "Volver al Inicio")
            }
        }
    }
}
