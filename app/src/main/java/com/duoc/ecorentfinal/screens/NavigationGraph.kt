package com.duoc.ecorentfinal.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.duoc.ecorentfinal.data.entities.HerramientasDataSource
import com.duoc.ecorentfinal.viewmodel.CarritoViewModel
import com.duoc.ecorentfinal.viewmodel.ItemCarrito

@Composable
fun EcoRentNavigation() {
    val navController = rememberNavController()

    // DEBUG: Importante - crear el ViewModel UNA vez
    println("🚀🚀🚀 NAVIGATIONGRAPH: Creando ViewModel...")
    val carritoViewModel: CarritoViewModel = viewModel()
    println("🚀🚀🚀 NAVIGATIONGRAPH: ViewModel creado - hashCode: ${carritoViewModel.hashCode()}")

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
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

        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("productos")
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                },
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable("productos") {
            ProductosScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
                onArrendarHerramienta = { herramientaId ->
                    navController.navigate("detalle/$herramientaId")
                }
            )
        }

        composable(
            route = "detalle/{herramientaId}",
            arguments = listOf(
                navArgument("herramientaId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val herramientaId = backStackEntry.arguments?.getLong("herramientaId") ?: 0L

            println("📍📍📍 NAV: Detalle pantalla para ID: $herramientaId")
            println("📍📍📍 NAV: ViewModel disponible - hashCode: ${carritoViewModel.hashCode()}")

            DetalleHerramientaScreen(
                herramientaId = herramientaId,
                onAgregarAlCarrito = { id, dias, costoTotal ->
                    println("🎯🎯🎯 NAV: Botón AGREGAR presionado!")
                    println("🎯🎯🎯 ID: $id, Días: $dias, Total: $costoTotal")
                    println("🎯🎯🎯 Usando ViewModel hashCode: ${carritoViewModel.hashCode()}")

                    val herramienta = HerramientasDataSource.herramientas.find { it.id == id }

                    if (herramienta != null) {
                        val nuevoItem = ItemCarrito(
                            productoId = id,
                            nombre = herramienta.nombre,
                            precioPorDia = herramienta.precioPorDia,
                            dias = dias,
                            costoTotal = costoTotal
                        )

                        // ¡AGREGAR AL MISMO ViewModel!
                        carritoViewModel.agregarAlCarrito(nuevoItem)

                        // Pequeño delay para asegurar que el estado se actualice
                        Thread.sleep(50) // Solo 50ms para no bloquear UI

                        // Navegar al carrito
                        println("➡️➡️➡️ NAV: Navegando a carrito después de agregar")
                        navController.navigate("carrito") {
                            popUpTo("productos") { inclusive = false }
                        }
                    }
                },
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable("carrito") {
            println("🛒🛒🛒 NAV: Navegando a CarritoScreen")
            println("🛒🛒🛒 NAV: Pasando ViewModel - hashCode: ${carritoViewModel.hashCode()}")

            // PASAR el ViewModel como parámetro
            CarritoScreen(
                carritoViewModel = carritoViewModel, // ← ¡IMPORTANTE!
                onNavigateBack = {
                    navController.navigate("productos")
                },
                onProcederPago = {
                    navController.navigate("checkout")
                }
            )
        }

        composable("checkout") {
            // DEBUG
            println("💳 NAVIGATIONGRAPH: Navegando a CheckoutScreen")
            println("💳 NAVIGATIONGRAPH: Pasando ViewModel - hashCode: ${carritoViewModel.hashCode()}")

            CheckoutScreen(
                carritoViewModel = carritoViewModel, // ← ¡PASAR EL ViewModel!
                onNavigateBack = {
                    navController.navigateUp()
                },
                onConfirmarPago = {
                    navController.navigate("confirmacion")
                }
            )
        }

        composable("confirmacion") {
            ConfirmacionScreen(
                onVolverAInicio = {
                    navController.navigate("home") {
                        popUpTo(0)
                    }
                }
            )
        }

        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("productos")
                },
                onNavigateToLogin = {
                    navController.navigate("login")
                },
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}