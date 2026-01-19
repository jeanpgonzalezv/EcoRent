package com.duoc.ecorentfinal.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun EcoRentNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // Pantalla de Inicio
        composable("home") {
            HomeScreen(
                onNavigateToLogin = {
                    navController.navigate("login")
                },
                onNavigateToProductos = {
                    navController.navigate("productos")
                }
            )
        }

        // Pantalla de Login
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    // Después de login exitoso, ir a productos
                    navController.navigate("productos") {
                        popUpTo("home") { inclusive = false }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                },
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }

        // Pantalla de Productos
        composable("productos") {
            ProductosScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
        // Pantalla de Registro
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    // Después de registro exitoso, ir a productos
                    navController.navigate("productos") {
                        popUpTo("home") { inclusive = false }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate("login")
                },
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
        // TODO: Agregar más pantallas aquí
        // composable("register") { ... }
        // composable("detalle/{id}") { ... }
    }
}